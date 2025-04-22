package backend.bids.consumers;

import static backend.common.config.kafka.KafkaGroups.CLOSED_AUCTION_GROUP;

import backend.bids.dynamodb.BidsDynamoService;
import backend.bids.entities.WinningBidEntity;
import backend.bids.repositories.WinningBidRepository;
import backend.common.config.kafka.KafkaTopics;
import backend.common.config.logging.AppLogger;
import backend.common.dynamodb.tables.Bids;
import backend.items.entities.ItemEntity;
import backend.items.repositories.ItemRepository;
import backend.users.entities.UserBillingEntity;
import backend.users.entities.UserEntity;
import backend.users.repositories.UserBillingRepository;
import backend.users.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Class that consumes and processes closed auctions.
 */
@Component
@Profile("!test")
public class ClosedAuctionConsumer {

  /**
   * Item repository to use.
   */
  private final ItemRepository itemRepository;

  /**
   * User billing repository.
   */
  private final UserBillingRepository userBillingRepository;

  /**
   * Winning bid repository.
   */
  private final WinningBidRepository winningBidRepository;

  /**
   * User repository.
   */
  private final UserRepository userRepository;

  /**
   * Object mapper to use.
   */
  private final ObjectMapper objectMapper;

  /**
   * Bids dynamo service to use.
   */
  private final BidsDynamoService bidsDynamoService;

  /**
   * The stripe api key.
   */
  @Value("${Stripe.apiKey}")
  private String stripeApiKey;


  /**
   * Constructor for the consumer.
   *
   * @param itemRepository        the item repository.
   * @param userBillingRepository the user billing repository.
   * @param winningBidRepository  the winning bid repository.
   * @param userRepository        the user repository.
   * @param objectMapper          an object mapper.
   * @param bidsDynamoService     the dynamo bids service.
   */
  @Autowired
  public ClosedAuctionConsumer(ItemRepository itemRepository,
                               UserBillingRepository userBillingRepository,
                               WinningBidRepository winningBidRepository,
                               UserRepository userRepository,
                               ObjectMapper objectMapper,
                               BidsDynamoService bidsDynamoService) {
    this.itemRepository = itemRepository;
    this.userBillingRepository = userBillingRepository;
    this.winningBidRepository = winningBidRepository;
    this.userRepository = userRepository;
    this.objectMapper = objectMapper;
    this.bidsDynamoService = bidsDynamoService;
  }

  /**
   * A post construct to initialise stripe.
   */
  @PostConstruct
  public void initStripe() {
    if (stripeApiKey == null || stripeApiKey.isBlank()) {
      AppLogger.warn("Stripe API key is not configured. Stripe API calls will fail.");
      throw new IllegalStateException("Stripe API key "
              + "('stripe.apiKey') is missing or empty in configuration.");
    } else {
      AppLogger.info("Initializing Stripe with configured API key.");
      Stripe.apiKey = stripeApiKey;
    }
  }

  /**
   * Kafka listener to consumer closed auctions.
   *
   * @param itemJson the item as json.
   */
  @KafkaListener(topics = KafkaTopics.CLOSED_AUCTION_TOPIC, groupId = CLOSED_AUCTION_GROUP)
  public void consumeClosedAuction(String itemJson) {
    try {
      ItemEntity item = objectMapper.readValue(itemJson, ItemEntity.class);
      AppLogger.info("Successfully processed closed auction item with ID: {}", item.getId());

      Bids finalBid = bidsDynamoService.getMostRecentBid(item.getId());

      itemRepository.updateFinalPrice(item.getId(), finalBid.getAmount());

      UserBillingEntity customerBilling = userBillingRepository.findById(finalBid.getUserId())
              .orElseThrow(() -> new EntityNotFoundException(
                      "UserBillingEntity not found for userId: " + finalBid.getUserId()));

      String customerId = customerBilling.getCustomerId();

      UserBillingEntity sellerBilling = userBillingRepository.findById(item.getSeller().getId())
              .orElseThrow(() -> new EntityNotFoundException(
                      "UserBillingEntity not found for userId: " + finalBid.getUserId()));

      String sellerId = sellerBilling.getAccountId();

      long amountInPence = finalBid.getAmount().multiply(new BigDecimal("100")).longValue();

      PaymentIntentCreateParams.Builder paramsBuilder = PaymentIntentCreateParams.builder()
              .setAmount(amountInPence)
              .setCurrency("GBP")
              .setCustomer(customerId)
              .setPaymentMethod(finalBid.getPaymentMethod())
              .setConfirm(true)
              .setOffSession(true)
              .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.AUTOMATIC)
              .setTransferData(
                      PaymentIntentCreateParams.TransferData.builder()
                              .setDestination(sellerId)
                              .build()
              )
              .putMetadata("item_id", item.getId().toString())
              .putMetadata("bidder_user_id", finalBid.getUserId().toString())
              .putMetadata("seller_user_id", item.getSeller().toString());

      PaymentIntent.create(paramsBuilder.build());

      UserEntity bidder = userRepository.findById(finalBid.getUserId())
              .orElseThrow(() -> new EntityNotFoundException(
                      "Bidder UserEntity not found for userId: " + finalBid.getUserId()));

      WinningBidEntity winningBid = getWinningBidEntity(item, finalBid, bidder);

      winningBidRepository.save(winningBid);
    } catch (JsonProcessingException e) {
      AppLogger.error("Failed to deserialize item: {}", e.getMessage(), e);
    } catch (StripeException e) {
      throw new RuntimeException(e);
    }
  }

  private static @NotNull WinningBidEntity getWinningBidEntity(
          ItemEntity item, Bids finalBid, UserEntity bidder) {
    UserEntity seller = item.getSeller();
    if (seller == null) {
      throw new IllegalStateException(
              "Seller information is missing on the ItemEntity for item ID: " + item.getId());
    }

    return new WinningBidEntity(
            finalBid.getBidId(),
            item,
            seller,
            bidder,
            finalBid.getAmount()
    );
  }
}

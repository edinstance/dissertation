package backend.bids.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating bids.
 */
@Getter
@Setter
public class CreateBidDto {

  /**
   * The bid id.
   */
  private UUID bidId;

  /**
   * The id of the user who created the bid.
   */
  private UUID userId;

  /**
   * The id of the item the bid is for.
   */
  private UUID itemId;

  /**
   * The amount of the bid.
   */
  private BigDecimal amount;

  /**
   * The payment method.
   */
  private String paymentMethod;

  /**
   * Default constructor.
   */
  public CreateBidDto() {
  }

  /**
   * Constructor with values.
   *
   * @param userId the user id.
   * @param itemId the item id.
   * @param amount the amount for the bid.
   */
  public CreateBidDto(UUID userId, UUID itemId,
                      BigDecimal amount, String paymentMethod) {
    this.bidId = UUID.randomUUID();
    this.userId = userId;
    this.itemId = itemId;
    this.amount = amount;
    this.paymentMethod = paymentMethod;
  }

}

package backend.bids.entities;

import backend.items.entities.ItemEntity;
import backend.users.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity for winning bids.
 */
@Entity
@Getter
@Setter
@Table(name = "winning_bids")
public class WinningBidEntity {

  @Id
  @Column(name = "bid_id", nullable = false)
  private UUID bidId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "item_id")
  private ItemEntity item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id", referencedColumnName = "user_id")
  private UserEntity seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id", referencedColumnName = "user_id")
  private UserEntity buyer;

  @Column(name = "final_price", precision = 10)
  private BigDecimal finalPrice;

  /**
   * Default constructor.
   */
  public WinningBidEntity() {
  }

  /**
   * Constructor for winning bid.
   *
   * @param item       the item.
   * @param seller     the seller.
   * @param buyer      the buyer.
   * @param finalPrice the final price.
   */
  public WinningBidEntity(UUID bidId, ItemEntity item, UserEntity seller,
                          UserEntity buyer, BigDecimal finalPrice) {
    this.bidId = bidId;
    this.item = item;
    this.seller = seller;
    this.buyer = buyer;
    this.finalPrice = finalPrice;
  }

}
package backend.bids.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateBidDto {

  private UUID bidId;

  private UUID userId;

  private UUID itemId;

  private BigDecimal amount;

  public CreateBidDto() {
  }

  public CreateBidDto(UUID bidId, UUID userId, UUID itemId, BigDecimal amount) {
    this.bidId = bidId;
    this.userId = userId;
    this.itemId = itemId;
    this.amount = amount;
  }

}

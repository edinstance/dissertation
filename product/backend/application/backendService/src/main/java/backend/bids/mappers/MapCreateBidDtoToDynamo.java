package backend.bids.mappers;

import backend.bids.dto.CreateBidDto;
import backend.common.dynamodb.tables.Bids;

/**
 * This mapper maps a bid dto to a dynamo entity.
 */
public class MapCreateBidDtoToDynamo {


  /**
   * This maps a createBid dto to a dynamo object.
   *
   * @param bidsDto the dto to map.
   *
   * @return the mapped object.
   */
  public static Bids mapBidDtoToDynamo(CreateBidDto bidsDto) {
    if (bidsDto == null) {
      return null;
    }

    return new Bids(
            bidsDto.getBidId(),
            bidsDto.getUserId(),
            bidsDto.getItemId(),
            bidsDto.getAmount(),
            bidsDto.getPaymentMethod()
    );
  }
}

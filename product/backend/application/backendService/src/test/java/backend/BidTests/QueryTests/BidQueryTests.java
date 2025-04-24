package backend.BidTests.QueryTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.bids.dynamodb.BidsDynamoService;
import backend.bids.queries.BidsQueries;
import backend.common.dynamodb.tables.Bids;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BidQueryTests {

  @Mock
  private BidsDynamoService bidsDynamoService;

  @InjectMocks
  private BidsQueries bidsQueries;

  private final UUID itemId = UUID.randomUUID();

  @Test
  void testGetItemBids() {
    when(bidsDynamoService.getItemBids(itemId)).thenReturn(List.of(new Bids()));

    List<Bids> bids = bidsQueries.getItemBidsById(itemId.toString());

    assertFalse(bids.isEmpty());
  }

  @Test
  void testGetItemBidsEmpty() {
    when(bidsDynamoService.getItemBids(itemId)).thenReturn(List.of());

    List<Bids> bids = bidsQueries.getItemBidsById(itemId.toString());

    assertTrue(bids.isEmpty());
  }
}

package backend.BidTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.bids.dto.CreateBidDto;
import backend.bids.mutations.BidMutations;
import backend.bids.services.BidMutationService;
import backend.common.dto.MutationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BidMutationTests {

  @Mock
  private BidMutationService bidMutationService;

  @InjectMocks
  private BidMutations bidMutations;

  private final CreateBidDto createBidDto = new CreateBidDto();

  @Test
  public void testSuccessfulSubmitBid() {
    when(bidMutationService.createBid(createBidDto)).thenReturn(true);

    MutationResponse result = bidMutations.submitBid(createBidDto);

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Bid placed successfully.");
  }

  @Test
  public void testUnsuccessfulSubmitBid() {
    when(bidMutationService.createBid(createBidDto)).thenReturn(false);

    MutationResponse result = bidMutations.submitBid(createBidDto);

    assertFalse(result.isSuccess());
    assert result.getMessage().equals("Error placing bid, please try again.");


  }
}

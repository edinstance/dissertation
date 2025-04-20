package backend.BidTests.ServiceTests;

import backend.bids.dynamodb.BidsDynamoService;
import backend.bids.helpers.BidCacheHelpers;
import backend.bids.helpers.BidHelpers;
import backend.bids.helpers.BidKafkaHelpers;
import backend.bids.services.BidMutationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class SetupBidMutationServiceTests {

  @Mock
  public BidHelpers bidHelpers;

  @Mock
  public BidCacheHelpers bidCacheHelpers;

  @Mock
  public BidKafkaHelpers bidKafkaHelpers;

  @Mock
  public BidsDynamoService bidsDynamoService;

  @InjectMocks
  public BidMutationService bidMutationService;
}

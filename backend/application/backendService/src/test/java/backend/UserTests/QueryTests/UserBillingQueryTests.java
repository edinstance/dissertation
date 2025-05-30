package backend.UserTests.QueryTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.users.entities.UserBillingEntity;
import backend.users.queries.UserBillingQueries;
import backend.users.services.UserBillingService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserBillingQueryTests {

  @Mock
  private UserBillingService userBillingService;

  @InjectMocks
  private UserBillingQueries userBillingQueries;

  private final UserBillingEntity userBillingEntity = new UserBillingEntity(UUID.randomUUID(), "AccountId", "CustomerId");

  @Test
  public void testGetUserBilling() {
    when(userBillingService.getUserBilling()).thenReturn(userBillingEntity);

    UserBillingEntity result = userBillingQueries.getUserBilling();

    assertNotNull(result);
    assertEquals(result.getUserId(), userBillingEntity.getUserId());
    assertEquals(result.getAccountId(), userBillingEntity.getAccountId());
    assertEquals(result.getCustomerId(), userBillingEntity.getCustomerId());

    verify(userBillingService).getUserBilling();
  }


}

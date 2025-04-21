package backend.UserTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.users.dto.UserBillingInput;
import backend.users.entities.UserBillingEntity;
import backend.users.mutations.UserBillingMutations;
import backend.users.services.UserBillingService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserBillingMutationTests {

  @Mock
  private UserBillingService userBillingService;

  @InjectMocks
  private UserBillingMutations userBillingMutations;

  private final UserBillingEntity userBillingEntity = new UserBillingEntity(UUID.randomUUID(), "AccountId", "CustomerId");
  private final UserBillingInput userBillingInput = new UserBillingInput("AccountId", "CustomerId");

  @Test
  public void testSaveUserBilling() {
    when(userBillingService.saveUserBilling(userBillingInput)).thenReturn(userBillingEntity);

    UserBillingEntity result = userBillingMutations.saveUserBilling(userBillingInput);

    assertNotNull(result);
    assertEquals(result.getUserId(), userBillingEntity.getUserId());
    assertEquals(result.getAccountId(), userBillingEntity.getAccountId());
    assertEquals(result.getCustomerId(), userBillingEntity.getCustomerId());

    verify(userBillingService).saveUserBilling(userBillingInput);
  }


}

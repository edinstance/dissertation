package backend.UserTests.QueryTests;

import backend.users.dto.UserBillingInput;
import backend.users.dto.UserDetailsInput;
import backend.users.entities.UserBillingEntity;
import backend.users.entities.UserDetailsEntity;
import backend.users.entities.UserEntity;
import backend.users.mappers.UserDetailsMapper;
import backend.users.mutations.UserBillingMutations;
import backend.users.mutations.UserDetailsMutations;
import backend.users.queries.UserBillingQueries;
import backend.users.services.UserBillingService;
import backend.users.services.UserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

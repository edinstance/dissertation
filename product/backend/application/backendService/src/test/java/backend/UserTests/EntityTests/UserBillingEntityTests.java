package backend.UserTests.EntityTests;

import backend.users.entities.UserBillingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserBillingEntityTests {

  private UserBillingEntity userBillingEntity;

  @Test
  public void testDefaultConstructor() {
    userBillingEntity = new UserBillingEntity();

    assertNotNull(userBillingEntity);
  }

  @Test
  public void testConstructor() {
    userBillingEntity = new UserBillingEntity(
            UUID.randomUUID(),
            "acc",
            "cus"
    );

    assertNotNull(userBillingEntity);
    assertNotNull(userBillingEntity.getUserId());
    assert userBillingEntity.getAccountId().equals("acc");
    assert userBillingEntity.getCustomerId().equals("cus");
  }

  @BeforeEach
  public void setUp() {
    userBillingEntity = new UserBillingEntity(
            UUID.randomUUID(),
            "acc",
            "cus"
    );
  }

  @Test
  public void testUserIdMethods() {
    UUID userId = UUID.randomUUID();
    userBillingEntity.setUserId(userId);
    assert userBillingEntity.getUserId().equals(userId);
  }

  @Test
  public void testAccountIdMethods() {
    userBillingEntity.setAccountId("new account");
    assert userBillingEntity.getAccountId().equals("new account");
  }

  @Test
  public void testCustomerIdMethods() {
    userBillingEntity.setCustomerId("new customer");
    assert userBillingEntity.getCustomerId().equals("new customer");
  }
}
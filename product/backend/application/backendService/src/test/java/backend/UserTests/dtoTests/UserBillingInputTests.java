package backend.UserTests.dtoTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.users.dto.UserBillingInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserBillingInputTests {

  private UserBillingInput userBillingInput;

  @Test
  public void testDefaultConstructor() {
    userBillingInput = new UserBillingInput();

    assertNotNull(userBillingInput);
  }

  @Test
  public void testConstructor() {
    userBillingInput = new UserBillingInput("accountId", "customerId");

    assertNotNull(userBillingInput);
    assertEquals("accountId", userBillingInput.getAccountId());
    assertEquals("customerId", userBillingInput.getCustomerId());
  }

  @BeforeEach
  public void setUp() {
    userBillingInput = new UserBillingInput("accountId", "customerId");
  }

  @Test
  public void testAccountIdMethods() {
    userBillingInput.setAccountId("new accountId");
    assertEquals("new accountId", userBillingInput.getAccountId());
  }

  @Test
  public void testCustomerIdMethods() {
    userBillingInput.setCustomerId("new customerId");
    assertEquals("new customerId", userBillingInput.getCustomerId());
  }
}

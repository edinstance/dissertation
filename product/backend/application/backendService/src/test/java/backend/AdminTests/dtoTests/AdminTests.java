package backend.AdminTests.dtoTests;

import backend.admin.dto.Admin;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTests {

  private static Admin admin;

  @Test
  public void testDefaultConstructor() {
    Admin admin = new Admin();
    assertNotNull(admin);
  }

  @Test
  public void testConstructor() {
    UUID id = UUID.randomUUID();
    Admin admin = new Admin(id, false, "ACTIVE", false, "test@test.com");
    assertNotNull(admin);
    assertEquals(admin.getUserId(), id);
    assertFalse(admin.isSuperAdmin());
    assertEquals("ACTIVE", admin.getStatus());
    assertFalse(admin.getIsDeleted());
    assertEquals("test@test.com", admin.getEmail());
  }

  @BeforeAll
  public static void setUp() {
    admin = new Admin(UUID.randomUUID(), false, "ACTIVE", false, "admin@test.com");
  }

  @Test
  public void testUserIdMethods() {
    UUID newId = UUID.randomUUID();
    admin.setUserId(newId);
    assertEquals(newId, admin.getUserId());
  }

  @Test
  public void testSuperAdminMethods() {
    admin.setSuperAdmin(true);
    assertTrue(admin.isSuperAdmin());
  }

  @Test
  public void testStatusMethods() {
    admin.setStatus("DEACTIVATED");
    assertEquals("DEACTIVATED", admin.getStatus());
  }

  @Test
  public void testIsDeletedMethods() {
    admin.setIsDeleted(true);
    assertTrue(admin.getIsDeleted());
  }

  @Test
  public void testEmailMethods() {
    admin.setEmail("test@test.com");
    assertEquals("test@test.com", admin.getEmail());
  }
}

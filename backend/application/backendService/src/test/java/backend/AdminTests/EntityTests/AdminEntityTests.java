package backend.AdminTests.EntityTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import backend.admin.entities.AdminEntity;
import backend.users.entities.UserEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdminEntityTests {

  private static AdminEntity adminEntity;

  @Test
  public void testDefaultConstructor() {
    AdminEntity adminEntity = new AdminEntity();
    assertNotNull(adminEntity);
    assertFalse(adminEntity.isSuperAdmin());
    assertFalse(adminEntity.getIsDeleted());
  }

  @Test
  public void testConstructor() {
    AdminEntity adminEntity = new AdminEntity(UUID.randomUUID(),
            false, "ACTIVE", UUID.randomUUID(), UUID.randomUUID());
    assertNotNull(adminEntity);
    assertFalse(adminEntity.isSuperAdmin());
    assertEquals("ACTIVE", adminEntity.getStatus());
  }

  @BeforeAll
  public static void setUp() {
    adminEntity = new AdminEntity(UUID.randomUUID(),
            false, "ACTIVE", UUID.randomUUID(), UUID.randomUUID());
  }

  @Test
  public void testIdMethods() {
    UUID newId = UUID.randomUUID();
    adminEntity.setUserId(newId);
    assertEquals(newId, adminEntity.getUserId());
  }

  @Test
  public void testSuperAdminMethods() {
    adminEntity.setSuperAdmin(true);
    assertTrue(adminEntity.isSuperAdmin());
  }

  @Test
  public void testStatusMethods() {
    adminEntity.setStatus("DEACTIVATED");
    assertEquals("DEACTIVATED", adminEntity.getStatus());
  }

  @Test
  public void testCreatedByMethods() {
    UUID newId = UUID.randomUUID();
    adminEntity.setCreatedBy(newId);
    assertEquals(newId, adminEntity.getCreatedBy());
  }

  @Test
  public void testLastUpdatedByMethods() {
    UUID newId = UUID.randomUUID();
    adminEntity.setLastUpdatedBy(newId);
    assertEquals(newId, adminEntity.getLastUpdatedBy());
  }

  @Test
  public void testIsDeletedMethods() {
    adminEntity.setIsDeleted(true);
    assertTrue(adminEntity.getIsDeleted());
  }

  @Test
  public void testUserMethods() {
    UserEntity user = new UserEntity();
    adminEntity.setUser(user);
    assertEquals(user, adminEntity.getUser());
  }

}

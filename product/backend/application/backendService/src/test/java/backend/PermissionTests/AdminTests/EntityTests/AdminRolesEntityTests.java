package backend.PermissionTests.AdminTests.EntityTests;

import backend.admin.entities.AdminEntity;
import backend.permissions.entities.RoleEntity;
import backend.permissions.entities.admin.AdminRolesEntity;
import backend.permissions.entities.admin.ids.AdminRolesEntityId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminRolesEntityTests {

  private static AdminRolesEntity adminRolesEntity;

  @Test
  public void testDefaultConstructor() {
    adminRolesEntity = new AdminRolesEntity();
    assertNotNull(adminRolesEntity);
  }

  @Test
  public void testConstructor() {
    adminRolesEntity = new AdminRolesEntity(new AdminEntity(), new RoleEntity());
    assertNotNull(adminRolesEntity);
    assertNotNull(adminRolesEntity.getRole());
    assertNotNull(adminRolesEntity.getAdmin());
  }

  @BeforeAll
  public static void setUp() {
    AdminEntity adminEntity = new AdminEntity(UUID.randomUUID(),
            false, "ACTIVE", UUID.randomUUID(), UUID.randomUUID());

    RoleEntity roleEntity = new RoleEntity(UUID.randomUUID(), "Name", "Description");

    adminRolesEntity = new AdminRolesEntity(adminEntity, roleEntity);
  }

  @Test
  public void testIdMethods() {
    AdminRolesEntityId newId = new AdminRolesEntityId();
    adminRolesEntity.setId(newId);
    assert adminRolesEntity.getId().equals(newId);
  }

  @Test
  public void testAdminMethods() {
    AdminEntity newAdmin = new AdminEntity();
    adminRolesEntity.setAdmin(newAdmin);
    assert adminRolesEntity.getAdmin().equals(newAdmin);
  }

  @Test
  public void testRoleMethods() {
    RoleEntity newRole = new RoleEntity();
    adminRolesEntity.setRole(newRole);
    assert adminRolesEntity.getRole().equals(newRole);
  }
}

package backend.PermissionTests.AdminTests.EntityTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.admin.entities.AdminEntity;
import backend.permissions.entities.ActionsEntity;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.entities.ResourcesEntity;
import backend.permissions.entities.admin.AdminPermissionsEntity;
import backend.permissions.entities.admin.ids.AdminPermissionsEntityId;
import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AdminPermissionsEntityTests {

  private static AdminPermissionsEntity adminPermissionsEntity;

  @Test
  public void testDefaultConstructor() {
    adminPermissionsEntity = new AdminPermissionsEntity();
    assertNotNull(adminPermissionsEntity);
  }

  @Test
  public void testConstructor() {
    AdminEntity adminEntity = new AdminEntity();
    PermissionsEntity permissionsEntity = new PermissionsEntity();
    adminPermissionsEntity = new AdminPermissionsEntity(adminEntity, permissionsEntity, "Read");
    assertNotNull(adminPermissionsEntity);
  }

  @BeforeAll
  public static void setUp() {
    AdminEntity adminEntity = new AdminEntity(UUID.randomUUID(),
            false, "ACTIVE", UUID.randomUUID(), UUID.randomUUID());


    ResourcesEntity resourcesEntity = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "Resource Description");
    ActionsEntity actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Action Description");

    PermissionsEntity permissionsEntity = new PermissionsEntity(resourcesEntity, actionsEntity, "Permission Description");
    permissionsEntity.setId(UUID.randomUUID());

    adminPermissionsEntity = new AdminPermissionsEntity(adminEntity, permissionsEntity, "Read");
  }

  @Test
  public void testIdMethods() {
    AdminPermissionsEntityId adminPermissionsEntityId = new AdminPermissionsEntityId();
    adminPermissionsEntity.setId(adminPermissionsEntityId);
    assert adminPermissionsEntity.getId().equals(adminPermissionsEntityId);
  }

  @Test
  public void testAdminMethods() {
    AdminEntity adminEntity = new AdminEntity();
    adminPermissionsEntity.setAdmin(adminEntity);
    assert adminPermissionsEntity.getAdmin().equals(adminEntity);
  }

  @Test
  public void testPermissionMethods() {
    PermissionsEntity permissionsEntity = new PermissionsEntity();
    adminPermissionsEntity.setPermission(permissionsEntity);
    assert adminPermissionsEntity.getPermission().equals(permissionsEntity);
  }

  @Test
  public void testGrantTypeMethods() {
    adminPermissionsEntity.setGrantType("Deny");
    assert adminPermissionsEntity.getGrantType().equals("Deny");
  }
}
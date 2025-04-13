package backend.PermissionTests.EntityTests;

import backend.permissions.entities.ActionsEntity;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.entities.ResourcesEntity;
import backend.permissions.entities.RoleEntity;
import backend.permissions.entities.RolePermissionEntity;
import backend.permissions.entities.ids.RolePermissionId;
import backend.permissions.types.Actions;
import backend.permissions.types.GrantType;
import backend.permissions.types.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RolePermissionEntityTests {

  private static RolePermissionEntity rolePermissionEntity;

  @Test
  public void testDefaultConstructor() {
    RolePermissionEntity entity = new RolePermissionEntity();
    assertNotNull(entity);
  }

  @Test
  public void testConstructor() {
    RoleEntity role = new RoleEntity();
    role.setId(UUID.randomUUID());
    role.setRoleName("TestRole");

    ResourcesEntity resource = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "Test Resource Description");
    ActionsEntity action = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Test Action Description");

    PermissionsEntity permission = new PermissionsEntity(resource, action, "Test Permission");
    permission.setId(UUID.randomUUID());

    RolePermissionEntity entity = new RolePermissionEntity(role, permission, GrantType.GRANT);

    assertNotNull(entity);
    assertEquals(role, entity.getRole());
    assertEquals(permission, entity.getPermission());
  }

  @BeforeAll
  public static void setUp() {
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setId(UUID.randomUUID());
    roleEntity.setRoleName("Admin");
    roleEntity.setDescription("Administrator role");

    ResourcesEntity resourcesEntity = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "Resource Description");
    ActionsEntity actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Action Description");

    PermissionsEntity permissionsEntity = new PermissionsEntity(resourcesEntity, actionsEntity, "Permission Description");
    permissionsEntity.setId(UUID.randomUUID());

    RolePermissionId rolePermissionId = new RolePermissionId();
    rolePermissionId.setRoleId(roleEntity.getId());
    rolePermissionId.setPermissionId(permissionsEntity.getId());

    rolePermissionEntity = new RolePermissionEntity(roleEntity, permissionsEntity, GrantType.GRANT);
    rolePermissionEntity.setId(rolePermissionId);
  }

  @Test
  public void testIdMethods() {
    RolePermissionId newId = new RolePermissionId();
    newId.setRoleId(UUID.randomUUID());
    newId.setPermissionId(UUID.randomUUID());

    rolePermissionEntity.setId(newId);
    assertEquals(newId, rolePermissionEntity.getId());
  }

  @Test
  public void testRoleMethods() {
    RoleEntity newRole = new RoleEntity();
    newRole.setId(UUID.randomUUID());
    newRole.setRoleName("NewRole");

    rolePermissionEntity.setRole(newRole);
    assertEquals(newRole, rolePermissionEntity.getRole());
  }

  @Test
  public void testPermissionMethods() {
    ResourcesEntity resource = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "New Resource Description");
    ActionsEntity action = new ActionsEntity(UUID.randomUUID(), Actions.READ, "New Action Description");

    PermissionsEntity newPermission = new PermissionsEntity(resource, action, "New Permission");
    newPermission.setId(UUID.randomUUID());

    rolePermissionEntity.setPermission(newPermission);
    assertEquals(newPermission, rolePermissionEntity.getPermission());
  }

  @Test
  public void testGrantTypeMethods() {
    rolePermissionEntity.setGrantType(GrantType.DENY);
    assertEquals(GrantType.DENY, rolePermissionEntity.getGrantType());
  }
}
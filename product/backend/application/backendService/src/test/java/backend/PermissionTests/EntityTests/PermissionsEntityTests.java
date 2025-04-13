package backend.PermissionTests.EntityTests;

import backend.permissions.entities.ActionsEntity;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.entities.ResourcesEntity;
import backend.permissions.entities.RolePermissionEntity;
import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PermissionsEntityTests {

  private static PermissionsEntity permissionsEntity;

  @BeforeAll
  public static void setUp() {
    ResourcesEntity resourcesEntity = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "Resource Description");
    ActionsEntity actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Action Description");
    permissionsEntity = new PermissionsEntity(resourcesEntity, actionsEntity, "Permission Description");
  }

  @Test
  public void testDefaultConstructor() {
    PermissionsEntity entity = new PermissionsEntity();
    assertNotNull(entity);
  }

  @Test
  public void testConstructor() {
    ResourcesEntity resource = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "Test Resource Description");
    ActionsEntity action = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Test Action Description");
    PermissionsEntity entity = new PermissionsEntity(resource, action, "Test Description");

    assertNotNull(entity);
    assertEquals(resource, entity.getResource());
    assertEquals(action, entity.getAction());
    assertEquals("Test Description", entity.getDescription());
  }

  @Test
  public void testIdMethods() {
    UUID newId = UUID.randomUUID();
    permissionsEntity.setId(newId);
    assertEquals(newId, permissionsEntity.getId());
  }

  @Test
  public void testResourceMethods() {
    ResourcesEntity newResource = new ResourcesEntity(UUID.randomUUID(), Resources.USERS, "New Resource Description");
    permissionsEntity.setResource(newResource);
    assertEquals(newResource, permissionsEntity.getResource());
  }

  @Test
  public void testActionMethods() {
    ActionsEntity newAction = new ActionsEntity(UUID.randomUUID(), Actions.READ, "New Action Description");
    permissionsEntity.setAction(newAction);
    assertEquals(newAction, permissionsEntity.getAction());
  }

  @Test
  public void testDescriptionMethods() {
    permissionsEntity.setDescription("Updated Description");
    assertEquals("Updated Description", permissionsEntity.getDescription());
  }

  @Test
  public void testRolePermissionsMethods() {
    Set<RolePermissionEntity> rolePermissions = new LinkedHashSet<>();
    RolePermissionEntity rolePermission = new RolePermissionEntity();
    rolePermissions.add(rolePermission);

    permissionsEntity.setRolePermissions(rolePermissions);
    assertEquals(rolePermissions, permissionsEntity.getRolePermissions());
  }
}
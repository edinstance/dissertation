package com.finalproject.backend.PermissionTests.EntityTests;

import com.finalproject.backend.permissions.entities.ActionsEntity;
import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.entities.ResourcesEntity;
import com.finalproject.backend.permissions.entities.RoleEntity;
import com.finalproject.backend.permissions.entities.RolePermissionEntity;
import com.finalproject.backend.permissions.entities.ids.RolePermissionId;
import com.finalproject.backend.permissions.types.Actions;
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

    ResourcesEntity resource = new ResourcesEntity(UUID.randomUUID(), "Test Resource", "Test Resource Description");
    ActionsEntity action = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Test Action Description");

    PermissionsEntity permission = new PermissionsEntity(resource, action, "Test Permission");
    permission.setId(UUID.randomUUID());

    RolePermissionEntity entity = new RolePermissionEntity(role, permission);

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

    ResourcesEntity resourcesEntity = new ResourcesEntity(UUID.randomUUID(), "Resource", "Resource Description");
    ActionsEntity actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Action Description");

    PermissionsEntity permissionsEntity = new PermissionsEntity(resourcesEntity, actionsEntity, "Permission Description");
    permissionsEntity.setId(UUID.randomUUID());
    
    RolePermissionId rolePermissionId = new RolePermissionId();
    rolePermissionId.setRoleId(roleEntity.getId());
    rolePermissionId.setPermissionId(permissionsEntity.getId());
    
    rolePermissionEntity = new RolePermissionEntity(roleEntity, permissionsEntity);
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
    ResourcesEntity resource = new ResourcesEntity(UUID.randomUUID(), "New Resource", "New Resource Description");
    ActionsEntity action = new ActionsEntity(UUID.randomUUID(), Actions.READ, "New Action Description");

    PermissionsEntity newPermission = new PermissionsEntity(resource, action, "New Permission");
    newPermission.setId(UUID.randomUUID());

    rolePermissionEntity.setPermission(newPermission);
    assertEquals(newPermission, rolePermissionEntity.getPermission());
  }
}
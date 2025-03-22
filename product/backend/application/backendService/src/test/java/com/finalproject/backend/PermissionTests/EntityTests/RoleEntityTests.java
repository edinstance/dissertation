package com.finalproject.backend.PermissionTests.EntityTests;

import com.finalproject.backend.permissions.entities.RoleEntity;
import com.finalproject.backend.permissions.entities.RolePermissionEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RoleEntityTests {

  private static RoleEntity roleEntity;

  @Test
  public void testDefaultConstructor() {
    RoleEntity entity = new RoleEntity();
    assertNotNull(entity);
  }

  @Test
  public void testConstructor() {
    RoleEntity roleEntity = new RoleEntity(UUID.randomUUID(), "Name", "Description");
    assertNotNull(roleEntity);

  }

  @BeforeAll
  public static void setUp() {
    roleEntity = new RoleEntity(UUID.randomUUID(), "Name", "Description");

  }

  @Test
  public void testIdMethods() {
    UUID newId = UUID.randomUUID();
    roleEntity.setId(newId);
    assertEquals(newId, roleEntity.getId());
  }

  @Test
  public void testRoleNameMethods() {
    String newRoleName = "SuperAdmin";
    roleEntity.setRoleName(newRoleName);
    assertEquals(newRoleName, roleEntity.getRoleName());
  }

  @Test
  public void testDescriptionMethods() {
    String newDescription = "Super Administrator role with enhanced privileges";
    roleEntity.setDescription(newDescription);
    assertEquals(newDescription, roleEntity.getDescription());
  }

  @Test
  public void testRolePermissionsMethods() {
    Set<RolePermissionEntity> rolePermissions = new LinkedHashSet<>();
    RolePermissionEntity permission1 = new RolePermissionEntity();
    RolePermissionEntity permission2 = new RolePermissionEntity();
    rolePermissions.add(permission1);
    rolePermissions.add(permission2);

    roleEntity.setRolePermissions(rolePermissions);
    assertEquals(rolePermissions, roleEntity.getRolePermissions());
    assertEquals(2, roleEntity.getRolePermissions().size());
  }
}
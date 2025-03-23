package com.finalproject.backend.PermissionTests.AdminTests.EntityTests.CompositeIdTests;

import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AdminPermissionsViewIdTest {

  @Test
  void testSelfEquality() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    AdminPermissionId id = new AdminPermissionId();
    id.setAdminId(adminId);
    id.setPermissionId(permissionId);
    id.setResourceId(resourceId);
    id.setActionId(actionId);

    assertEquals(id, id);
  }

  @Test
  void testNullInequality() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    AdminPermissionId id = new AdminPermissionId();
    id.setAdminId(adminId);
    id.setPermissionId(permissionId);
    id.setResourceId(resourceId);
    id.setActionId(actionId);

    assertNotEquals(null, id);
  }

  @Test
  void testEqualObjects() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    AdminPermissionId id2 = new AdminPermissionId();
    id2.setAdminId(adminId);
    id2.setPermissionId(permissionId);
    id2.setResourceId(resourceId);
    id2.setActionId(actionId);


    assertEquals(id1, id2);
    assertEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentAdminId() {
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(UUID.randomUUID());
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(sharedResourceId);
    id1.setActionId(sharedActionId);

    AdminPermissionId id2 = new AdminPermissionId();
    id2.setAdminId(UUID.randomUUID());
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(sharedResourceId);
    id2.setActionId(sharedActionId);

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentPermissionId() {
    UUID sharedAdminId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(sharedAdminId);
    id1.setPermissionId(UUID.randomUUID());
    id1.setResourceId(sharedResourceId);
    id1.setActionId(sharedActionId);

    AdminPermissionId id2 = new AdminPermissionId();
    id2.setAdminId(sharedAdminId);
    id2.setPermissionId(UUID.randomUUID());
    id2.setResourceId(sharedResourceId);
    id2.setActionId(sharedActionId);

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentResourceId() {
    UUID sharedAdminId = UUID.randomUUID();
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(sharedAdminId);
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(UUID.randomUUID());
    id1.setActionId(sharedActionId);

    AdminPermissionId id2 = new AdminPermissionId();
    id2.setAdminId(sharedAdminId);
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(UUID.randomUUID());
    id2.setActionId(sharedActionId);


    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentActionId() {
    UUID sharedAdminId = UUID.randomUUID();
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(sharedAdminId);
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(sharedResourceId);
    id1.setActionId(UUID.randomUUID());

    AdminPermissionId id2 = new AdminPermissionId();
    id2.setAdminId(sharedAdminId);
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(sharedResourceId);
    id2.setActionId(UUID.randomUUID());

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testGettersAndSetters() {
    AdminPermissionId id1 = new AdminPermissionId();

    UUID newAdminId = UUID.randomUUID();
    UUID newPermissionId = UUID.randomUUID();
    UUID newResourceId = UUID.randomUUID();
    UUID newActionId = UUID.randomUUID();

    id1.setAdminId(newAdminId);
    id1.setPermissionId(newPermissionId);
    id1.setResourceId(newResourceId);
    id1.setActionId(newActionId);

    assertEquals(newAdminId, id1.getAdminId());
    assertEquals(newPermissionId, id1.getPermissionId());
    assertEquals(newResourceId, id1.getResourceId());
    assertEquals(newActionId, id1.getActionId());
  }

  @Test
  void testNullComparison() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    assertFalse(id1.equals(null));
  }

  @Test
  void testDifferentClassComparison() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    AdminPermissionId id1 = new AdminPermissionId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    assertFalse(id1.equals("String"));
  }
}

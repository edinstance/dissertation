package com.finalproject.backend.PermissionTests.AdminTests.EntityTests.CompositeIdTests;

import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AdminPermissionsViewIdTest {

  @Test
  void testEqualsAndHashCode() {
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

    AdminPermissionId id3 = new AdminPermissionId();
    id3.setAdminId(UUID.randomUUID());
    id3.setPermissionId(permissionId);
    id3.setResourceId(resourceId);
    id3.setActionId(actionId);

    AdminPermissionId id4 = new AdminPermissionId();
    id4.setAdminId(adminId);
    id4.setPermissionId(UUID.randomUUID());
    id4.setResourceId(resourceId);
    id4.setActionId(actionId);

    assertEquals(id1, id1);
    assertNotEquals(null, id1);

    assertEquals(id1, id2);
    assertEquals(id1.hashCode(), id2.hashCode());

    assertNotEquals(id1, id3);
    assertNotEquals(id1.hashCode(), id3.hashCode());

    assertNotEquals(id1, id4);
    assertNotEquals(id1.hashCode(), id4.hashCode());
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

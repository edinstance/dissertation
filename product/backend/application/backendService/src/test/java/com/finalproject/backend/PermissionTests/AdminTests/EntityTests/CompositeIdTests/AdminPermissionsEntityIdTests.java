package com.finalproject.backend.PermissionTests.AdminTests.EntityTests.CompositeIdTests;

import com.finalproject.backend.permissions.entities.admin.ids.AdminPermissionsEntityId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdminPermissionsEntityIdTests {

  @Test
  void testEqualsAndHashCode() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();

    AdminPermissionsEntityId id1 = new AdminPermissionsEntityId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);

    AdminPermissionsEntityId id2 = new AdminPermissionsEntityId();
    id2.setAdminId(adminId);
    id2.setPermissionId(permissionId);

    AdminPermissionsEntityId id3 = new AdminPermissionsEntityId();
    id3.setAdminId(UUID.randomUUID());
    id3.setPermissionId(permissionId);

    AdminPermissionsEntityId id4 = new AdminPermissionsEntityId();
    id4.setAdminId(adminId);
    id4.setPermissionId(UUID.randomUUID());

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
    AdminPermissionsEntityId id1 = new AdminPermissionsEntityId();

    UUID newAdminId = UUID.randomUUID();
    UUID newPermissionId = UUID.randomUUID();

    id1.setAdminId(newAdminId);
    id1.setPermissionId(newPermissionId);

    assertEquals(newAdminId, id1.getAdminId());
    assertEquals(newPermissionId, id1.getPermissionId());
  }

  @Test
  void testNullComparison() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();

    AdminPermissionsEntityId id1 = new AdminPermissionsEntityId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);

    assertNotEquals(null, id1);
  }

  @Test
  void testDifferentClassComparison() {
    UUID adminId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();

    AdminPermissionsEntityId id1 = new AdminPermissionsEntityId();
    id1.setAdminId(adminId);
    id1.setPermissionId(permissionId);

    assertNotEquals("String", id1);
  }
}

package com.finalproject.backend.PermissionTests.EntityTests.CompositeIdTests;

import com.finalproject.backend.permissions.entities.ids.RolePermissionId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RolePermissionIdTests {

  @Test
  void testEqualsAndHashCode() {
    UUID roleId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();

    RolePermissionId id1 = new RolePermissionId();
    id1.setRoleId(roleId);
    id1.setPermissionId(permissionId);

    RolePermissionId id2 = new RolePermissionId();
    id2.setRoleId(roleId);
    id2.setPermissionId(permissionId);

    RolePermissionId id3 = new RolePermissionId();
    id3.setRoleId(UUID.randomUUID());
    id3.setPermissionId(permissionId);

    RolePermissionId id4 = new RolePermissionId();
    id4.setRoleId(roleId);
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
    RolePermissionId id1 = new RolePermissionId();

    UUID newRoleId = UUID.randomUUID();
    UUID newPermissionId = UUID.randomUUID();

    id1.setRoleId(newRoleId);
    id1.setPermissionId(newPermissionId);

    assertEquals(newRoleId, id1.getRoleId());
    assertEquals(newPermissionId, id1.getPermissionId());
  }

  @Test
  void testNullComparison() {
    UUID permissionId = UUID.randomUUID();
    UUID roleId = UUID.randomUUID();

    RolePermissionId id1 = new RolePermissionId();
    id1.setPermissionId(permissionId);
    id1.setRoleId(roleId);

    assertFalse(id1.equals(null));
  }

  @Test
  void testDifferentClassComparison() {
    UUID roleId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();

    RolePermissionId id1 = new RolePermissionId();
    id1.setRoleId(roleId);
    id1.setPermissionId(permissionId);

    assertFalse(id1.equals("String"));
  }
}

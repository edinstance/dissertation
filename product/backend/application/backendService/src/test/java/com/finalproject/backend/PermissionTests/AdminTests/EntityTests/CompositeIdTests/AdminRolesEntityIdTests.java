package com.finalproject.backend.PermissionTests.AdminTests.EntityTests.CompositeIdTests;

import com.finalproject.backend.permissions.entities.admin.ids.AdminRolesEntityId;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AdminRolesEntityIdTests {

  @Test
  void testEqualsAndHashCode() {
    UUID adminId = UUID.randomUUID();
    UUID roleId = UUID.randomUUID();

    AdminRolesEntityId id1 = new AdminRolesEntityId();
    id1.setAdminId(adminId);
    id1.setRoleId(roleId);

    AdminRolesEntityId id2 = new AdminRolesEntityId();
    id2.setAdminId(adminId);
    id2.setRoleId(roleId);

    AdminRolesEntityId id3 = new AdminRolesEntityId();
    id3.setAdminId(UUID.randomUUID());
    id3.setRoleId(roleId);

    AdminRolesEntityId id4 = new AdminRolesEntityId();
    id4.setAdminId(adminId);
    id4.setRoleId(UUID.randomUUID());

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
    AdminRolesEntityId id1 = new AdminRolesEntityId();

    UUID newAdminId = UUID.randomUUID();
    UUID newRoleId = UUID.randomUUID();

    id1.setAdminId(newAdminId);
    id1.setRoleId(newRoleId);

    assertEquals(newAdminId, id1.getAdminId());
    assertEquals(newRoleId, id1.getRoleId());
  }

  @Test
  void testNullComparison() {
    UUID adminId = UUID.randomUUID();
    UUID roleId = UUID.randomUUID();

    AdminRolesEntityId id1 = new AdminRolesEntityId();
    id1.setAdminId(adminId);
    id1.setRoleId(roleId);

    assertFalse(id1.equals(null));
  }

  @Test
  void testDifferentClassComparison() {
    UUID adminId = UUID.randomUUID();
    UUID roleId = UUID.randomUUID();

    AdminRolesEntityId id1 = new AdminRolesEntityId();
    id1.setAdminId(adminId);
    id1.setRoleId(roleId);

    assertFalse(id1.equals("String"));
  }
}

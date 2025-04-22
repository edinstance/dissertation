package backend.PermissionTests.EntityTests.CompositeIdTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import backend.permissions.entities.ids.PermissionViewId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class PermissionsViewIdTest {

  @Test
  void testSelfEquality() {
    UUID userId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    PermissionViewId id = new PermissionViewId();
    id.setUserId(userId);
    id.setPermissionId(permissionId);
    id.setResourceId(resourceId);
    id.setActionId(actionId);

    assertEquals(id, id);
  }

  @Test
  void testNullInequality() {
    UUID userId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    PermissionViewId id = new PermissionViewId();
    id.setUserId(userId);
    id.setPermissionId(permissionId);
    id.setResourceId(resourceId);
    id.setActionId(actionId);

    assertNotEquals(null, id);
  }

  @Test
  void testEqualObjects() {
    UUID userId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(userId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    PermissionViewId id2 = new PermissionViewId();
    id2.setUserId(userId);
    id2.setPermissionId(permissionId);
    id2.setResourceId(resourceId);
    id2.setActionId(actionId);


    assertEquals(id1, id2);
    assertEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentUserId() {
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(UUID.randomUUID());
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(sharedResourceId);
    id1.setActionId(sharedActionId);

    PermissionViewId id2 = new PermissionViewId();
    id2.setUserId(UUID.randomUUID());
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(sharedResourceId);
    id2.setActionId(sharedActionId);

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentPermissionId() {
    UUID sharedUserId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(sharedUserId);
    id1.setPermissionId(UUID.randomUUID());
    id1.setResourceId(sharedResourceId);
    id1.setActionId(sharedActionId);

    PermissionViewId id2 = new PermissionViewId();
    id2.setUserId(sharedUserId);
    id2.setPermissionId(UUID.randomUUID());
    id2.setResourceId(sharedResourceId);
    id2.setActionId(sharedActionId);

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentResourceId() {
    UUID sharedUserId = UUID.randomUUID();
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedActionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(sharedUserId);
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(UUID.randomUUID());
    id1.setActionId(sharedActionId);

    PermissionViewId id2 = new PermissionViewId();
    id2.setUserId(sharedUserId);
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(UUID.randomUUID());
    id2.setActionId(sharedActionId);


    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testDifferentActionId() {
    UUID sharedUserId = UUID.randomUUID();
    UUID sharedPermissionId = UUID.randomUUID();
    UUID sharedResourceId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(sharedUserId);
    id1.setPermissionId(sharedPermissionId);
    id1.setResourceId(sharedResourceId);
    id1.setActionId(UUID.randomUUID());

    PermissionViewId id2 = new PermissionViewId();
    id2.setUserId(sharedUserId);
    id2.setPermissionId(sharedPermissionId);
    id2.setResourceId(sharedResourceId);
    id2.setActionId(UUID.randomUUID());

    assertNotEquals(id1, id2);
    assertNotEquals(id1.hashCode(), id2.hashCode());
  }

  @Test
  void testGettersAndSetters() {
    PermissionViewId id1 = new PermissionViewId();

    UUID newUserId = UUID.randomUUID();
    UUID newPermissionId = UUID.randomUUID();
    UUID newResourceId = UUID.randomUUID();
    UUID newActionId = UUID.randomUUID();

    id1.setUserId(newUserId);
    id1.setPermissionId(newPermissionId);
    id1.setResourceId(newResourceId);
    id1.setActionId(newActionId);

    assertEquals(newUserId, id1.getUserId());
    assertEquals(newPermissionId, id1.getPermissionId());
    assertEquals(newResourceId, id1.getResourceId());
    assertEquals(newActionId, id1.getActionId());
  }

  @Test
  void testNullComparison() {
    UUID userId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(userId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    assertNotEquals(null, id1);
  }

  @Test
  void testDifferentClassComparison() {
    UUID userId = UUID.randomUUID();
    UUID permissionId = UUID.randomUUID();
    UUID resourceId = UUID.randomUUID();
    UUID actionId = UUID.randomUUID();

    PermissionViewId id1 = new PermissionViewId();
    id1.setUserId(userId);
    id1.setPermissionId(permissionId);
    id1.setResourceId(resourceId);
    id1.setActionId(actionId);

    assertNotEquals("String", id1);
  }
}

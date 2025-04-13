package backend.PermissionTests.UtilTests;

import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import backend.permissions.utils.PermissionKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PermissionKeyTests {

  private static final UUID associatedUserId = UUID.randomUUID();
  private static final Resources resource = Resources.USERS;
  private static final Actions action = Actions.READ;
  private static PermissionKey key;

  @BeforeAll
  public static void setUp() {
    key = new PermissionKey(associatedUserId, resource, action);
  }

  @Test
  public void testConstructor() {
    assertNotNull(key);
  }

  @Test
  public void testHashCodeEquality() {
    int hash1 = key.hashCode();
    assertEquals(hash1, key.hashCode());
  }

  @Test
  public void testEqualsItself() {
    assertEquals(key, key);
  }

  @Test
  public void testDifferentObjects() {
    assertNotEquals("String", key);
  }

  @Test
  public void testEqualKeys() {
    PermissionKey key2 = new PermissionKey(associatedUserId, resource, action);
    assertEquals(key, key2);
  }

  @Test
  public void testEqualIds() {
    PermissionKey key2 = new PermissionKey(associatedUserId, Resources.PERMISSIONS, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testEqualResources() {
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), resource, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testEqualActions() {
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), Resources.PERMISSIONS, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testNotEqual() {
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), Resources.ADMINS, Actions.CREATE);
    assertNotEquals(key, key2);
  }

  @Test
  public void testNotEqualNull() {
    PermissionKey key2 = null;
    assertNotEquals(key2, key);
  }

  @Test
  public void testNotEqualDifferentAction() {
    PermissionKey key2 = new PermissionKey(associatedUserId, resource, Actions.CREATE);
    assertNotEquals(key, key2);
  }
}

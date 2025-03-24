package com.finalproject.backend.PermissionTests.UtilTests;

import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.permissions.utils.PermissionKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionKeyTests {

  private static final UUID associatedUserId = UUID.randomUUID();
  private static final Resources resource = Resources.USERS;
  private static final Actions action = Actions.READ;
  private static PermissionKey key;

  @Test
  public void testConstructor() {
    key = new PermissionKey(associatedUserId, resource, action);
    assertNotNull(key);
  }

  @BeforeAll
    public static void setUp(){
        key = new PermissionKey(associatedUserId, resource, action);
    }

  @Test
  public void testHashCodeEquality(){
    int hash1 = key.hashCode();
    assertEquals(hash1, key.hashCode());
  }

  @Test
  public void testEqualsItself(){
    assertEquals(key, key);
  }

  @Test
  public void testDifferentObjects(){
    assertFalse(key.equals("String"));
  }

  @Test
  public void testEqualIds(){
    PermissionKey key2 = new PermissionKey(associatedUserId, Resources.PERMISSIONS, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testEqualResources(){
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), resource, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testEqualActions(){
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), Resources.PERMISSIONS, action);
    assertNotEquals(key, key2);
  }

  @Test
  public void testNotEqual(){
    PermissionKey key2 = new PermissionKey(UUID.randomUUID(), Resources.ADMINS, Actions.CREATE);
    assertNotEquals(key, key2);
  }

}

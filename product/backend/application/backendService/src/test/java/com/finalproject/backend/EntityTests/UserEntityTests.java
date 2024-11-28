package com.finalproject.backend.EntityTests;

import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityTests {

  private UserEntity userEntity;

  @Test
  public void testDefaultConstructor() {
    userEntity = new UserEntity();

    assertNotNull(userEntity);
  }

  @Test
  public void testConstructor() {
    userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name", "PENDING");

    assertNotNull(userEntity);
    assertNotNull(userEntity.getId());
    assert userEntity.getEmail().equals("test@test.com");
    assert userEntity.getName().equals("name");
    assert userEntity.getStatus().equals("PENDING");
  }

  @Test
  public void testConstructorWithStatus() {
    userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name", "APPROVED");

    assertNotNull(userEntity);
    assertNotNull(userEntity.getId());
    assert userEntity.getEmail().equals("test@test.com");
    assert userEntity.getName().equals("name");
    assert userEntity.getStatus().equals("APPROVED");
  }

  @BeforeEach
  public void setUp() {
    userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name");
  }

  @Test
  public void testIdMethods() {
    UUID userId = UUID.randomUUID();
    userEntity.setId(userId);
    assert userEntity.getId().equals(userId);
  }

  @Test
  public void testEmailMethods() {
    userEntity.setEmail("test2@test.com");
    assert userEntity.getEmail().equals("test2@test.com");
  }

  @Test
  public void testNameMethods() {
    userEntity.setName("name2");
    assert userEntity.getName().equals("name2");
  }

  @Test
  public void testStatusMethods() {
    userEntity.setStatus("APPROVED");
    assert userEntity.getStatus().equals("APPROVED");
  }

  @Test
  public void testToString() {
    UUID userId = UUID.randomUUID();
    UserDetailsEntity userDetails = new UserDetailsEntity(
            userId, "1234567890", "123 Test St",
            "Test City", "Test County", "12345"
    );
    UserEntity user = new UserEntity(userId, "test@example.com",
            "Test User", "ACTIVE");
    user.setUserDetailsEntity(userDetails);

    String expected = "UserEntity{" +
            "id=" + userId +
            ", email='test@example.com'" +
            ", name='Test User'" +
            ", status='ACTIVE'" +
            ", userDetailsEntity=" + userDetails +
            '}';

    assertEquals(expected, user.toString());
  }

  @Test
  public void testToStringWithNullDetails() {
    UUID userId = UUID.randomUUID();
    UserEntity user = new UserEntity(userId, "test@example.com",
            "Test User", "ACTIVE");

    String expected = "UserEntity{" +
            "id=" + userId +
            ", email='test@example.com'" +
            ", name='Test User'" +
            ", status='ACTIVE'" +
            ", userDetailsEntity=null" +
            '}';

    assertEquals(expected, user.toString());
  }
}
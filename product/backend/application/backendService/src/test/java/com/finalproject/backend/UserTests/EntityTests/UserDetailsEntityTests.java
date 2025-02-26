package com.finalproject.backend.UserTests.EntityTests;

import com.finalproject.backend.users.entities.UserDetailsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsEntityTests {

  private UserDetailsEntity userDetailsEntity;

  @Test
  public void testDefaultConstructor() {
    userDetailsEntity = new UserDetailsEntity();

    assertNotNull(userDetailsEntity);
  }

  @Test
  public void testConstructor() {
    userDetailsEntity = new UserDetailsEntity(
            UUID.randomUUID(),
            "11111111111",
            "Name",
            "Street",
            "City",
            "County",
            "AB123CD"
    );

    assertNotNull(userDetailsEntity);
    assertNotNull(userDetailsEntity.getId());
    assert userDetailsEntity.getContactNumber().equals("11111111111");
    assert userDetailsEntity.getHouseName().equals("Name");
    assert userDetailsEntity.getAddressStreet().equals("Street");
    assert userDetailsEntity.getAddressCity().equals("City");
    assert userDetailsEntity.getAddressCounty().equals("County");
    assert userDetailsEntity.getAddressPostcode().equals("AB123CD");
  }

  @BeforeEach
  public void setUp() {
    userDetailsEntity = new UserDetailsEntity(
            UUID.randomUUID(),
            "1234567890",
            "Name",
            "Street",
            "City",
            "County",
            "AB123CD"
    );
  }

  @Test
  public void testIdMethods() {
    UUID userId = UUID.randomUUID();
    userDetailsEntity.setId(userId);
    assert userDetailsEntity.getId().equals(userId);
  }

  @Test
  public void testContactNumberMethods() {
    userDetailsEntity.setContactNumber("0987654321");
    assert userDetailsEntity.getContactNumber().equals("0987654321");
  }

  @Test
  public void testHouseNameMethods() {
    userDetailsEntity.setHouseName("Test");
    assert userDetailsEntity.getHouseName().equals("Test");
  }

  @Test
  public void testAddressStreetMethods() {
    userDetailsEntity.setAddressStreet("456 Test Ave");
    assert userDetailsEntity.getAddressStreet().equals("456 Test Ave");
  }

  @Test
  public void testAddressCityMethods() {
    userDetailsEntity.setAddressCity("Another City");
    assert userDetailsEntity.getAddressCity().equals("Another City");
  }

  @Test
  public void testAddressCountyMethods() {
    userDetailsEntity.setAddressCounty("Another County");
    assert userDetailsEntity.getAddressCounty().equals("Another County");
  }

  @Test
  public void testAddressPostCodeMethods() {
    userDetailsEntity.setAddressPostcode("AB123CE");
    assert userDetailsEntity.getAddressPostcode().equals("AB123CE");
  }

  @Test
  public void testToString() {
    UUID userId = UUID.randomUUID();
    UserDetailsEntity userDetails = new UserDetailsEntity(
            userId, "1234567890", "Name", "123 Test St", "Test City", "Test County", "12345"
    );

    String expected = "UserDetailsEntity{" +
            "id=" + userId +
            ", contactNumber='1234567890'" +
            ", houseName='Name'" +
            ", addressStreet='123 Test St'" +
            ", addressCity='Test City'" +
            ", addressCounty='Test County'" +
            ", addressPostcode='12345'" +
            '}';

    assertEquals(expected, userDetails.toString());
  }
}
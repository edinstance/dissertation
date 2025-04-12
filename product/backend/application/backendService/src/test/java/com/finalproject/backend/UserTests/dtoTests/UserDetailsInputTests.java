package com.finalproject.backend.UserTests.dtoTests;

import com.finalproject.backend.users.dto.UserDetailsInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsInputTests {

  private UserDetailsInput userDetailsInput;

  @Test
  public void testDefaultConstructor() {
    userDetailsInput = new UserDetailsInput();

    assertNotNull(userDetailsInput);
  }

  @Test
  public void testUserDetailsInputConstructor() {
    userDetailsInput = new UserDetailsInput("1234567890", "house name", "123 Test St", "Test City", "Test County", "12345");

    assertNotNull(userDetailsInput);
    assertEquals("1234567890", userDetailsInput.getContactNumber());
    assertEquals("house name", userDetailsInput.getHouseName());
    assertEquals("123 Test St", userDetailsInput.getAddressStreet());
    assertEquals("Test City", userDetailsInput.getAddressCity());
    assertEquals("Test County", userDetailsInput.getAddressCounty());
    assertEquals("12345", userDetailsInput.getAddressPostcode());
  }

  @BeforeEach
  public void setUp() {
    userDetailsInput = new UserDetailsInput("1234567890", "house name", "123 Test St", "Test City", "Test County", "12345");
  }

  @Test
  public void testContactNumberMethods() {
    userDetailsInput.setContactNumber("0987654321");
    assertEquals("0987654321", userDetailsInput.getContactNumber());
  }

  @Test
  public void testHouseNameMethods() {
    userDetailsInput.setHouseName("house name 2");
    assertEquals("house name 2", userDetailsInput.getHouseName());
  }

  @Test
  public void testAddressStreetMethods() {
    userDetailsInput.setAddressStreet("456 Test Ave");
    assertEquals("456 Test Ave", userDetailsInput.getAddressStreet());
  }

  @Test
  public void testAddressCityMethods() {
    userDetailsInput.setAddressCity("Another City");
    assertEquals("Another City", userDetailsInput.getAddressCity());
  }

  @Test
  public void testAddressCountyMethods() {
    userDetailsInput.setAddressCounty("Another County");
    assertEquals("Another County", userDetailsInput.getAddressCounty());
  }

  @Test
  public void testAddressPostcodeMethods() {
    userDetailsInput.setAddressPostcode("67890");
    assertEquals("67890", userDetailsInput.getAddressPostcode());
  }
}
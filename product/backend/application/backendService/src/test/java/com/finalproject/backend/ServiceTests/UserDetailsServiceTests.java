package com.finalproject.backend.ServiceTests;

import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.UserHelpers;
import com.finalproject.backend.repositories.UserDetailsRepository;
import com.finalproject.backend.services.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {

  @Mock
  private UserDetailsRepository userDetailsRepository;

  @Mock
  private UserHelpers userHelpers;

  @InjectMocks
  private UserDetailsService userDetailsService;

  private UUID userId;
  private UserDetailsEntity userDetails;
  private UserEntity userEntity;

  @BeforeEach
  public void setUp() {
    userId = UUID.randomUUID();
    userDetails = new UserDetailsEntity(userId, "1234567890", "name", "Street", "City", "County", "AB12C34");
    userEntity = new UserEntity(userId, "email", "name");
    userEntity.setUserDetailsEntity(userDetails);
  }

  @Test
  public void testCreateOrUpdateDetails() {
    // Mock the helper method to return the user entity
    when(userHelpers.getUserById(userId)).thenReturn(userEntity);

    // Call the method to test
    UserEntity returnedUser = userDetailsService.saveUserDetails(userDetails);

    // Verify that the repository method was called with the correct parameters
    verify(userDetailsRepository, times(1)).saveUserDetails(
            userDetails.getId(),
            userDetails.getContactNumber(),
            userDetails.getHouseName(),
            userDetails.getAddressStreet(),
            userDetails.getAddressCity(),
            userDetails.getAddressCounty(),
            userDetails.getAddressPostcode()
    );

    // Verify the returned user entity
    assertEquals(userId, returnedUser.getId());
    assertEquals("email", returnedUser.getEmail());
    assertEquals("name", returnedUser.getName());
    assertEquals("PENDING", returnedUser.getStatus());

    UserDetailsEntity returnedDetails = returnedUser.getUserDetailsEntity();
    assertNotNull(returnedDetails);
    assertEquals(userId, returnedDetails.getId());
    assertEquals("1234567890", returnedDetails.getContactNumber());
    assertEquals("name", returnedDetails.getHouseName());
    assertEquals("Street", returnedDetails.getAddressStreet());
    assertEquals("City", returnedDetails.getAddressCity());
    assertEquals("County", returnedDetails.getAddressCounty());
    assertEquals("AB12C34", returnedDetails.getAddressPostcode());

  }
}
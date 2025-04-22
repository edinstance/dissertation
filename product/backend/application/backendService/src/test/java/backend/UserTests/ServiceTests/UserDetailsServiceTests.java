package backend.UserTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.common.helpers.AuthHelpers;
import backend.users.entities.UserDetailsEntity;
import backend.users.entities.UserEntity;
import backend.users.helpers.UserHelpers;
import backend.users.repositories.UserDetailsRepository;
import backend.users.services.UserDetailsService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {

  @Mock
  private UserDetailsRepository userDetailsRepository;

  @Mock
  private UserHelpers userHelpers;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private JedisPool jedisPool;

  @Mock
  private Jedis jedis;

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
    when(jedisPool.getResource()).thenReturn(jedis);

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

  @Test
  public void testSaveUserCaching() {
    when(userHelpers.getUserById(userId)).thenReturn(userEntity);
    when(jedisPool.getResource()).thenReturn(jedis);
    userDetailsService.saveUserDetails(userDetails);


    verify(userDetailsRepository, times(1)).saveUserDetails(
            userDetails.getId(),
            userDetails.getContactNumber(),
            userDetails.getHouseName(),
            userDetails.getAddressStreet(),
            userDetails.getAddressCity(),
            userDetails.getAddressCounty(),
            userDetails.getAddressPostcode()
    );

    verify(jedisPool, times(1)).getResource();
    verify(jedis, times(1))
            .set(eq("user:" + userId), anyString(), any(SetParams.class));
  }

  @Test
  public void testCheckCurrentUserDetailsExist() {
    when(authHelpers.getCurrentUserId()).thenReturn(userId);
    when(userDetailsRepository.existsById(userId)).thenReturn(true);
    assertTrue(userDetailsService.checkCurrentUserDetailsExist());
    verify(userDetailsRepository, times(1)).existsById(userId);

    when(userDetailsRepository.existsById(userId)).thenReturn(false);
    assertFalse(userDetailsService.checkCurrentUserDetailsExist());
    verify(userDetailsRepository, times(2)).existsById(userId);
  }
}
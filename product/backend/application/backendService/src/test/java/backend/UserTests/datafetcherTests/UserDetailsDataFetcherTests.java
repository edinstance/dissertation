package backend.UserTests.datafetcherTests;

import backend.users.datafetchers.UserDetailsDataFetcher;
import backend.users.entities.UserDetailsEntity;
import backend.users.entities.UserEntity;
import backend.users.helpers.UserHelpers;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsDataFetcherTests {

  @Mock
  private UserHelpers userHelpers;

  @Mock
  private DgsDataFetchingEnvironment dfe;

  @InjectMocks
  private UserDetailsDataFetcher userDetailsDataFetcher;

  private UserEntity userEntity;
  private UserDetailsEntity userDetailsEntity;

  @BeforeEach
  public void setUp() {
    UUID userId = UUID.randomUUID();
    userDetailsEntity = new UserDetailsEntity(userId, "1234567890", "name", "123 Test St", "Test City", "Test County", "12345");
    userEntity = new UserEntity(userId, "test@example.com", "Test User", "ACTIVE");
    userEntity.setUserDetailsEntity(userDetailsEntity);
  }

  @Test
  public void testGetUserDetails() {
    when(dfe.getSource()).thenReturn(userEntity);
    when(userHelpers.getUserById(any(UUID.class))).thenReturn(userEntity);

    UserDetailsEntity result = userDetailsDataFetcher.getUserDetails(dfe);

    assertNotNull(result);
    assertEquals(userDetailsEntity, result);
  }

  @Test
  public void testGetUserIsNull() {
    when(dfe.getSource()).thenReturn(null);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      userDetailsDataFetcher.getUserDetails(dfe);
    });

    assertEquals("User not found", exception.getMessage());

  }
}
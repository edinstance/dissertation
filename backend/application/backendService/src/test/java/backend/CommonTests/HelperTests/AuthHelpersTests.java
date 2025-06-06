package backend.CommonTests.HelperTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import backend.common.helpers.AuthHelpers;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@ExtendWith(MockitoExtension.class)
public class AuthHelpersTests {

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @Mock
  private Jwt jwt;

  @InjectMocks
  private AuthHelpers authHelpers;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(jwt);
  }

  @Test
  public void testGetUserId() {
    String expectedUserId = "123e4567-e89b-12d3-a456-426614174000";
    when(jwt.getClaimAsString("sub")).thenReturn(expectedUserId);

    UUID result = authHelpers.getCurrentUserId();

    assertEquals(UUID.fromString(expectedUserId), result);
  }

  @Test
  void testGetUserWithNoJwt() {
    when(authentication.getPrincipal()).thenReturn("");

    List<String> result = authHelpers.getCurrentUserGroups();

    assert result.isEmpty();
  }

  @Test
  void testGetUserGroups() {
    when(jwt.getClaimAsStringList("cognito:groups")).thenReturn(List.of("admin"));

    List<String> result = authHelpers.getCurrentUserGroups();

    assertEquals(List.of("admin"), result);
  }

  @Test
  void testGetUserNoGroups() {
    when(jwt.getClaimAsStringList("cognito:groups")).thenReturn(List.of());

    List<String> result = authHelpers.getCurrentUserGroups();

    assert result.isEmpty();
  }

  @Test
  void testGetUserGroupsError() {
    when(authentication.getPrincipal()).thenReturn("Not a JWT");

    List<String> result = authHelpers.getCurrentUserGroups();

    assert result.isEmpty();
  }
}

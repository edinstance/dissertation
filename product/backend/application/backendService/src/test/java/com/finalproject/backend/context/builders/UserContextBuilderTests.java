package com.finalproject.backend.context.builders;

import com.finalproject.backend.context.UserContext;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UserContextBuilder class.
 */
@ExtendWith(MockitoExtension.class)
class UserContextBuilderTest {

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @Mock
  private Jwt jwt;

  @InjectMocks
  private UserContextBuilder userContextBuilder;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(jwt);
  }

  /**
   * Tests the build method of UserContextBuilder.
   */
  @Test
  void testBuildUserContext() {
    String expectedUserId = "test-user-id";
    when(jwt.getClaimAsString("sub")).thenReturn(expectedUserId);

    UserContext userContext = userContextBuilder.build();

    assertEquals(expectedUserId, userContext.getUserId());
  }
}
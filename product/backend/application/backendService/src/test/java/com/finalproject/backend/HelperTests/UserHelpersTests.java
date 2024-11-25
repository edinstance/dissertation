package com.finalproject.backend.HelperTests;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.UserHelpers;
import com.finalproject.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserHelpersTests {

  @Mock
  private SecurityContext securityContext;

  @Mock
  private Authentication authentication;

  @Mock
  private Jwt jwt;

  @Mock
  private UserService userService;

  @InjectMocks
  private UserHelpers userHelpers;

  @Test
  public void testGetUserByIdHelper() {

    UserEntity userEntity = new UserEntity(UUID.randomUUID(),
            "email", "name", "PENDING");

    when(userService.getUserById(userEntity.getId())).thenReturn(userEntity);

    UserEntity foundUser = userHelpers.getUserById(userEntity.getId());

    assert foundUser.getId().equals(userEntity.getId());
    assert foundUser.getEmail().equals(userEntity.getEmail());
    assert foundUser.getName().equals(userEntity.getName());
    assert foundUser.getStatus().equals(userEntity.getStatus());

  }

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

    UUID result = userHelpers.getCurrentUserId();

    assertEquals(UUID.fromString(expectedUserId), result);
  }

  @Test
  void testGetUserWithNoJwt() {
    when(authentication.getPrincipal()).thenReturn("");

    IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
      userHelpers.getCurrentUserId();
    });

    assertEquals("Unable to extract user ID from token", exception.getMessage());
  }
}

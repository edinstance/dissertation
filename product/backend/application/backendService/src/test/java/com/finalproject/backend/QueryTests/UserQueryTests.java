package com.finalproject.backend.QueryTests;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.AuthHelpers;
import com.finalproject.backend.queries.UserQueries;
import com.finalproject.backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the UserQueries class.
 */
@ExtendWith(MockitoExtension.class)
class UserQueryTests {

  @Mock
  private UserService userService;

  @Mock
  private AuthHelpers authHelpers;

  @InjectMocks
  private UserQueries userQueries;

  private UserEntity userEntity;

  @BeforeEach
  void setUp() {
    userEntity = new UserEntity(UUID.randomUUID(), "email", "name");

  }

  /**
   * Tests the getUser method of UserQueries.
   */
  @Test
  void testGetUser() {
    UUID expectedUserId = (userEntity.getId());
    when(authHelpers.getCurrentUserId()).thenReturn(expectedUserId);
    when(userService.getUserById(expectedUserId)).thenReturn(userEntity);

    UserEntity result = userQueries.getUser();

    assertEquals(userEntity, result);

  }
}

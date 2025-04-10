package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.Exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GetAllUsersTests {


  @Mock
  private UserRepository userRepository;

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private AdminService adminService;


  @Test
  public void getAllUsersNoResultsTest() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    when(userRepository.findAll()).thenReturn(List.of());

    List<UserEntity> result = adminService.getAllUsers();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void getAllUsersTest() {

    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);

    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "email@test.com", "name");
    when(userRepository.findAll()).thenReturn(List.of(userEntity));

    List<UserEntity> result = adminService.getAllUsers();

    assertNotNull(result);
    assertTrue(result.contains(userEntity));
    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void testNoPermissions() {
    when(authHelpers.getCurrentUserId()).thenReturn(UUID.randomUUID());
    when(adminAuthorizer.authorize(
            any(UUID.class),
            eq(Resources.USERS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class, () -> {
      adminService.getAllUsers();
    });

    assert exception.getMessage().equals("Admin does not have permission to view user data");
  }
}
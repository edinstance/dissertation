package backend.AdminTests.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.admin.services.AdminService;
import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import backend.users.entities.UserEntity;
import backend.users.repositories.UserRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
package backend.PermissionTests.ServiceTests;

import backend.common.exceptions.UnauthorisedException;
import backend.common.helpers.AuthHelpers;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.entities.PermissionsEntity;
import backend.permissions.repositories.PermissionsRepository;
import backend.permissions.services.PermissionsService;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTests {

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private PermissionsRepository permissionsRepository;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private PermissionsService permissionsService;

  private final UUID adminId = UUID.randomUUID();

  @Test
  public void testCreatePermissionUnauthorized() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.PERMISSIONS), eq(Actions.CREATE), eq(AdminViewTypes.ALL))).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class,
            () -> permissionsService.createPermission(
                    "Test Permission",
                    Actions.READ,
                    "Test Action",
                    Resources.USERS,
                    "Test Resource"
            ));

    assertTrue(exception.getMessage().contains("Admin is not authorized to create permissions"));
  }

  @Test
  public void testCreatePermission() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);

    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.PERMISSIONS), eq(Actions.CREATE), eq(AdminViewTypes.ALL))).thenReturn(true);

    boolean result = permissionsService.createPermission(
            "Test Permission",
            Actions.READ,
            "Test Action",
            Resources.USERS,
            "Test Resource");

    assertTrue(result);
    verify(permissionsRepository, times(1)).createPermission(
            "Test Permission",
            Actions.READ.name(),
            "Test Action",
            Resources.USERS.name(),
            "Test Resource"
    );
  }

  @Test
  public void testGetAllPermissionsUnauthorized() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class,
            () -> permissionsService.getAllPermissions());

    assertTrue(exception.getMessage().contains("Admin is not authorized to read permissions"));
  }

  @Test
  public void testGetAllPermissions() {
    PermissionsEntity permissionsEntity = new PermissionsEntity();

    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);
    when(permissionsRepository.findAll()).thenReturn(List.of(permissionsEntity));

    List<PermissionsEntity> result = permissionsService.getAllPermissions();

    assertEquals(1, result.size());
    verify(permissionsRepository, times(1)).findAll();

  }
}
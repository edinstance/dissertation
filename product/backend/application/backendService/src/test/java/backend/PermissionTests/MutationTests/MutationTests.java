package backend.PermissionTests.MutationTests;

import backend.common.dto.MutationResponse;
import backend.permissions.dto.CreatePermissionInput;
import backend.permissions.mutations.PermissionsMutations;
import backend.permissions.services.PermissionsService;
import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MutationTests {

  @Mock
  private PermissionsService permissionsService;

  @InjectMocks
  private PermissionsMutations permissionsMutations;

  private final CreatePermissionInput createPermissionInput = new CreatePermissionInput(
          "Test Permission",
          Actions.READ,
          "Test Action Description",
          Resources.USERS,
          "Test Resource Description"
  );

  @Test
  public void testCreatePermission() {
    when(permissionsService.createPermission("Test Permission",
            Actions.READ,
            "Test Action Description",
            Resources.USERS,
            "Test Resource Description")).thenReturn(true);

    MutationResponse result = permissionsMutations.createPermission(createPermissionInput);

    assertTrue(result.isSuccess());
    assertEquals("Permission created successfully", result.getMessage());
  }

  @Test
  public void testCreatePermissionFailure() {
    when(permissionsService.createPermission("Test Permission",
            Actions.READ,
            "Test Action Description",
            Resources.USERS,
            "Test Resource Description")).thenReturn(false);

    MutationResponse result = permissionsMutations.createPermission(createPermissionInput);

    assertFalse(result.isSuccess());
    assertEquals("Error creating permission", result.getMessage());
  }
}

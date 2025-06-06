package backend.PermissionTests.MutationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.common.dto.MutationResponse;
import backend.permissions.mutations.AdminPermissionsMutations;
import backend.permissions.services.AdminPermissionsService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminMutationTests {

  @Mock
  private AdminPermissionsService adminPermissionsService;

  @InjectMocks
  private AdminPermissionsMutations adminPermissionsMutations;

  public final UUID adminId = UUID.randomUUID();
  public final UUID permissionId = UUID.randomUUID();

  @Test
  public void testRevokeAdminPermission() {
    when(adminPermissionsService.revokeAdminPermission(adminId, permissionId)).thenReturn(true);

    MutationResponse result = adminPermissionsMutations.revokeAdminPermission(adminId.toString(), permissionId.toString());

    assertTrue(result.isSuccess());
    assertEquals("Admin permission revoked successfully", result.getMessage());
  }

  @Test
  public void testRevokeAdminPermissionFailure() {
    when(adminPermissionsService.revokeAdminPermission(adminId, permissionId)).thenReturn(false);

    MutationResponse result = adminPermissionsMutations.revokeAdminPermission(adminId.toString(), permissionId.toString());

    assertFalse(result.isSuccess());
    assertEquals("Error revoking admin permission", result.getMessage());
  }

  @Test
  public void testGrantAdminPermission() {
    when(adminPermissionsService.grantAdminPermissions(adminId, permissionId)).thenReturn(true);

    MutationResponse result = adminPermissionsMutations.grantAdminPermission(adminId.toString(), permissionId.toString());

    assertTrue(result.isSuccess());
    assertEquals("Admin permission granted successfully", result.getMessage());
  }

  @Test
  public void testGrantAdminPermissionFailure() {
    when(adminPermissionsService.grantAdminPermissions(adminId, permissionId)).thenReturn(false);

    MutationResponse result = adminPermissionsMutations.grantAdminPermission(adminId.toString(), permissionId.toString());

    assertFalse(result.isSuccess());
    assertEquals("Error granting admin permission", result.getMessage());
  }
}

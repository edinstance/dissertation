package com.finalproject.backend.PermissionTests.MutationTests;

import com.finalproject.backend.admin.mutations.AdminMutations;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.dto.MutationResponse;
import com.finalproject.backend.permissions.mutations.AdminPermissionsMutations;
import com.finalproject.backend.permissions.services.AdminPermissionsService;
import java.util.UUID;
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
}

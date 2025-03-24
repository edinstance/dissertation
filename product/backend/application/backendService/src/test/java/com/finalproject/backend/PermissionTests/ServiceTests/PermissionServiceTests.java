package com.finalproject.backend.PermissionTests.ServiceTests;

import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.services.PermissionsService;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.Resources;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTests {

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private PermissionViewRepository permissionViewRepository;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private PermissionsService permissionsService;

  private final UUID adminId = UUID.randomUUID();

  @Test
  public void testGetAllAdminPermissionsEmpty() {
    when(permissionViewRepository.getAllAdminPermissions(AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of());
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);

    List<PermissionView> allAdminPermissions = permissionsService.getAllAdminPermissions();

    assertTrue(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAllAdminPermissions() {
    when(permissionViewRepository.getAllAdminPermissions(AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of(new PermissionView()));
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);

    List<PermissionView> allAdminPermissions = permissionsService.getAllAdminPermissions();

    assertFalse(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAllAdminPermissionsNotAuthorized() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class, () -> permissionsService.getAllAdminPermissions());

    assertTrue(exception.getMessage().contains("Admin is not authorized to view all admin permissions"));
  }

  @Test
  public void testGetAdminPermissionsByIdEmpty() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of());

    List<PermissionView> allAdminPermissions = permissionsService.getCurrentAdminPermissions();

    assertTrue(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsById() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of(new PermissionView()));

    List<PermissionView> allAdminPermissions = permissionsService.getCurrentAdminPermissions();

    assertFalse(allAdminPermissions.isEmpty());
  }

}

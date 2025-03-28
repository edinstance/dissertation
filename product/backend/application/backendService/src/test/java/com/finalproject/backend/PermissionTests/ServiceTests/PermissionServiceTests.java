package com.finalproject.backend.PermissionTests.ServiceTests;

import com.finalproject.backend.common.exceptions.UnauthorisedException;
import com.finalproject.backend.common.helpers.AuthHelpers;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.entities.PermissionsEntity;
import com.finalproject.backend.permissions.entities.ids.PermissionViewId;
import com.finalproject.backend.permissions.repositories.PermissionViewRepository;
import com.finalproject.backend.permissions.repositories.PermissionsRepository;
import com.finalproject.backend.permissions.services.PermissionsService;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.AdminViewTypes;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PermissionServiceTests {

  @Mock
  private AuthHelpers authHelpers;

  @Mock
  private PermissionViewRepository permissionViewRepository;

  @Mock
  private PermissionsRepository permissionsRepository;

  @Mock
  private AdminAuthorizer adminAuthorizer;

  @InjectMocks
  private PermissionsService permissionsService;

  private final UUID adminId = UUID.randomUUID();
  private final PermissionViewId permissionViewId = new PermissionViewId(adminId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
  private final PermissionView permissionView = new PermissionView(permissionViewId, GrantType.GRANT, Resources.ADMIN_PERMISSIONS, Actions.READ);

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
  public void testGetCurrentAdminPermissionsByIdEmpty() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of());

    List<PermissionView> allAdminPermissions = permissionsService.getCurrentAdminPermissions();

    assertTrue(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetCurrentAdminPermissionsById() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of(new PermissionView()));

    List<PermissionView> allAdminPermissions = permissionsService.getCurrentAdminPermissions();

    assertFalse(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsByIdEmpty() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of());
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);

    List<PermissionsEntity> allAdminPermissions = permissionsService.getAdminPermissionsById(adminId);

    assertTrue(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsById() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(List.of(permissionView));
    when(permissionsRepository.findAllById(ArgumentMatchers.any()))
            .thenReturn(List.of(new PermissionsEntity()));
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);

    List<PermissionsEntity> allAdminPermissions = permissionsService.getAdminPermissionsById(adminId);

    assertFalse(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsNullViews() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(permissionViewRepository.getAdminPermissionsById(adminId, AdminViewTypes.ALL.getViewTypeName())).thenReturn(null);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(true);

    List<PermissionsEntity> allAdminPermissions = permissionsService.getAdminPermissionsById(adminId);

    assertTrue(allAdminPermissions.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsEmptyIds() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);

    PermissionView newView = new PermissionView(new PermissionViewId(adminId, null, null, null), GrantType.GRANT, Resources.ADMIN_PERMISSIONS, Actions.READ);

    when(adminAuthorizer.authorize(
            eq(adminId),
            eq(Resources.ADMIN_PERMISSIONS),
            eq(Actions.READ),
            eq(AdminViewTypes.ALL)
    )).thenReturn(true);


    when(permissionViewRepository.getAdminPermissionsById(
            eq(adminId),
            eq(AdminViewTypes.ALL.getViewTypeName())
    )).thenReturn(List.of(newView));

    List<PermissionsEntity> result = permissionsService.getAdminPermissionsById(adminId);

    assertTrue(result.isEmpty());
  }

  @Test
  public void testGetAdminPermissionsByIdUnauthorized() {
    when(authHelpers.getCurrentUserId()).thenReturn(adminId);
    when(adminAuthorizer.authorize(eq(adminId), eq(Resources.ADMIN_PERMISSIONS), eq(Actions.READ), eq(AdminViewTypes.ALL))).thenReturn(false);

    UnauthorisedException exception = assertThrows(UnauthorisedException.class, () -> permissionsService.getAdminPermissionsById(adminId));

    assertTrue(exception.getMessage().contains("Admin is not authorized to view different admin permissions"));
  }

}

package com.finalproject.backend.PermissionTests.AuthorizerTests;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.repositories.AdminRepository;
import com.finalproject.backend.permissions.admin.entities.AdminPermissionView;
import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import com.finalproject.backend.permissions.admin.repositories.AdminPermissionViewRepository;
import com.finalproject.backend.permissions.authorizers.AdminAuthorizer;
import com.finalproject.backend.permissions.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminAuthorizerTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private AdminPermissionViewRepository adminPermissionViewRepository;

  @InjectMocks
  private AdminAuthorizer adminAuthorizer;

  private UUID adminId;
  private AdminEntity admin;
  private Resources resources;
  private Actions actions;
  private GrantType grantType;
  private ViewTypes viewTypes;

  @BeforeEach
  public void setUp() {
    adminId = UUID.randomUUID();
    admin = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);
    resources = Resources.ADMINS;
    actions = Actions.READ;
    grantType = GrantType.GRANT;
    viewTypes = AdminViewTypes.PERMISSIONS;
  }

  @Test
  public void testIncorrectViewType() {
    viewTypes = UserViewType.USER;

    Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes)
    );

    assert exception.getMessage().equals("Expected an AdminPermissionViewTypes, got: " + viewTypes.getViewTypeName());
  }

  @Test
  public void testAdminIsNull(){
    when(adminRepository.findById(adminId)).thenReturn(Optional.empty());
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testSuperAdmin(){
    admin.setSuperAdmin(true);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    assertTrue(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminHasPermissions(){
    AdminPermissionId adminPermissionId = new AdminPermissionId();
    adminPermissionId.setAdminId(adminId);
    AdminPermissionView adminPermissionView = new AdminPermissionView(adminPermissionId, grantType, resources, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of(adminPermissionView));
    assertTrue(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminHasNoPermissions(){
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of());
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminHasWrongPermissions(){
    AdminPermissionId adminPermissionId = new AdminPermissionId();
    adminPermissionId.setAdminId(UUID.randomUUID());
    AdminPermissionView adminPermissionView = new AdminPermissionView(adminPermissionId, grantType, resources, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of(adminPermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminDifferentResources(){
    AdminPermissionId adminPermissionId = new AdminPermissionId();
    adminPermissionId.setAdminId(adminId);
    AdminPermissionView adminPermissionView = new AdminPermissionView(adminPermissionId, grantType, Resources.USERS, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of(adminPermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminDifferentActions(){
    AdminPermissionId adminPermissionId = new AdminPermissionId();
    adminPermissionId.setAdminId(adminId);
    AdminPermissionView adminPermissionView = new AdminPermissionView(adminPermissionId, grantType, resources, Actions.WRITE);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of(adminPermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

  @Test
  public void testAdminDifferentGrantType(){
    AdminPermissionId adminPermissionId = new AdminPermissionId();
    adminPermissionId.setAdminId(adminId);
    AdminPermissionView adminPermissionView = new AdminPermissionView(adminPermissionId, GrantType.DENY, resources, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(adminPermissionViewRepository.getAllAdminPermissions((AdminViewTypes) viewTypes)).thenReturn(List.of(adminPermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, grantType, viewTypes));
  }

}

package backend.PermissionTests.AuthorizerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import backend.admin.entities.AdminEntity;
import backend.admin.repositories.AdminRepository;
import backend.permissions.authorizers.AdminAuthorizer;
import backend.permissions.entities.PermissionView;
import backend.permissions.entities.ids.PermissionViewId;
import backend.permissions.repositories.PermissionViewRepository;
import backend.permissions.types.Actions;
import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.GrantType;
import backend.permissions.types.Resources;
import backend.permissions.types.ViewTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminAuthorizerTests {

  @Mock
  private AdminRepository adminRepository;

  @Mock
  private PermissionViewRepository permissionViewRepository;

  @InjectMocks
  private AdminAuthorizer adminAuthorizer;

  private UUID adminId;
  private AdminEntity admin;
  private Resources resources;
  private Actions actions;
  private GrantType grantType;
  private ViewTypes viewTypes;
  private PermissionViewId permissionId;
  private PermissionView permissionView;
  private List<PermissionView> allPermissionViews;

  @BeforeEach
  public void setUp() {
    adminId = UUID.randomUUID();
    admin = new AdminEntity(adminId, false, "ACTIVE", adminId, adminId);
    resources = Resources.ADMINS;
    actions = Actions.READ;
    grantType = GrantType.GRANT;
    viewTypes = AdminViewTypes.PERMISSIONS;

    permissionId = new PermissionViewId();
    permissionId.setUserId(adminId);
    permissionView = new PermissionView(permissionId, GrantType.GRANT, resources, actions);

    allPermissionViews = new ArrayList<>();
    allPermissionViews.add(permissionView);
  }

  @Test
  public void testAdminIsNull() {
    when(adminRepository.findById(adminId)).thenReturn(Optional.empty());
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testSuperAdmin() {
    admin.setSuperAdmin(true);
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    assertTrue(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testAdminHasPermissions() {
    PermissionViewId PermissionViewId = new PermissionViewId();
    PermissionViewId.setUserId(adminId);
    PermissionView PermissionView = new PermissionView(PermissionViewId, grantType, resources, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(permissionViewRepository.getAdminPermissionsById(adminId, viewTypes.getViewTypeName())).thenReturn(List.of(PermissionView));
    assertTrue(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testAdminHasNoPermissions() {
    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(permissionViewRepository.getAdminPermissionsById(adminId, viewTypes.getViewTypeName())).thenReturn(List.of());
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testAdminHasWrongPermissions() {
    PermissionViewId PermissionViewId = new PermissionViewId();
    PermissionViewId.setUserId(UUID.randomUUID());
    PermissionView PermissionView = new PermissionView(PermissionViewId, grantType, resources, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(permissionViewRepository.getAdminPermissionsById(adminId, viewTypes.getViewTypeName())).thenReturn(List.of(PermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testAdminDifferentResources() {
    PermissionViewId PermissionViewId = new PermissionViewId();
    PermissionViewId.setUserId(adminId);
    PermissionView PermissionView = new PermissionView(PermissionViewId, grantType, Resources.USERS, actions);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(permissionViewRepository.getAdminPermissionsById(adminId, viewTypes.getViewTypeName())).thenReturn(List.of(PermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testAdminDifferentActions() {
    PermissionViewId PermissionViewId = new PermissionViewId();
    PermissionViewId.setUserId(adminId);
    PermissionView PermissionView = new PermissionView(PermissionViewId, grantType, resources, Actions.WRITE);

    when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));
    when(permissionViewRepository.getAdminPermissionsById(adminId, viewTypes.getViewTypeName())).thenReturn(List.of(PermissionView));
    assertFalse(adminAuthorizer.authorize(adminId, resources, actions, viewTypes));
  }

  @Test
  public void testEffectiveGrantPermissions() {
    List<PermissionView> effectivePermissions = adminAuthorizer.getEffectivePermissions(allPermissionViews);
    assertEquals(effectivePermissions.size(), allPermissionViews.size());

    assert effectivePermissions.contains(permissionView);
  }

  @Test
  public void testEffectiveGrantPermissionsWithDeny() {
    PermissionView newAdminView = new PermissionView(permissionId, GrantType.DENY, Resources.ADMIN_ROLES, actions);
    allPermissionViews.add(newAdminView);

    List<PermissionView> effectivePermissions = adminAuthorizer.getEffectivePermissions(allPermissionViews);

    assert !effectivePermissions.contains(newAdminView);
    assert effectivePermissions.contains(permissionView);
  }

  @Test
  public void testEffectiveGrantPermissionsWithConflict() {
    PermissionView newAdminView = new PermissionView(permissionId, GrantType.DENY, resources, actions);
    allPermissionViews.add(newAdminView);

    List<PermissionView> effectivePermissions = adminAuthorizer.getEffectivePermissions(allPermissionViews);

    assert effectivePermissions.isEmpty();
  }
}

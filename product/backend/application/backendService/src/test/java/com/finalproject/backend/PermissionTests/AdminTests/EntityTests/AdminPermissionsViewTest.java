package com.finalproject.backend.PermissionTests.AdminTests.EntityTests;

import com.finalproject.backend.permissions.admin.entities.AdminPermissionView;
import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminPermissionsViewTest {

  @Test
  public void testDefaultConstructor() {
    AdminPermissionView view = new AdminPermissionView();
    assertNotNull(view);
  }

  @Test
  public void testConstructor() {
    AdminPermissionView view = new AdminPermissionView(new AdminPermissionId(),
            GrantType.GRANT, Resources.ADMINS, Actions.READ);
    assertNotNull(view);

    assert view.getGrantType() == GrantType.GRANT;
    assert view.getResource() == Resources.ADMINS;
    assert view.getAction() == Actions.READ;
  }

  @Test
  public void testGrantTypeMethods() {
    AdminPermissionView view = new AdminPermissionView();
    view.setGrantType(GrantType.DENY);

    assert view.getGrantType() == GrantType.DENY;
  }

  @Test
  public void testResourceMethods() {
    AdminPermissionView view = new AdminPermissionView();
    view.setResource(Resources.USERS);

    assert view.getResource() == Resources.USERS;
  }

  @Test
    public void testActionMethods() {
        AdminPermissionView view = new AdminPermissionView();
        view.setAction(Actions.READ);

        assert view.getAction() == Actions.READ;
    }

    @Test
    public void testAssociatedUserIdMethods() {
        AdminPermissionView view = new AdminPermissionView();

        UUID id = UUID.randomUUID();
        AdminPermissionId adminId = new AdminPermissionId();
        adminId.setAdminId(id);
        view.setId(adminId);
        
        assert view.getAssociatedUserId() == id;
    }

}

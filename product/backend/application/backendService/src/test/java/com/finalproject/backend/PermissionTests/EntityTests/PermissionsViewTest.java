package com.finalproject.backend.PermissionTests.EntityTests;

import com.finalproject.backend.permissions.entities.PermissionView;
import com.finalproject.backend.permissions.entities.ids.PermissionViewId;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PermissionsViewTest {

  @Test
  public void testDefaultConstructor() {
    PermissionView view = new PermissionView();
    assertNotNull(view);
  }

  @Test
  public void testConstructor() {
    PermissionView view = new PermissionView(new PermissionViewId(),
            GrantType.GRANT, Resources.ADMINS, Actions.READ);
    assertNotNull(view);

    assert view.getGrantType() == GrantType.GRANT;
    assert view.getResource() == Resources.ADMINS;
    assert view.getAction() == Actions.READ;
  }

  @Test
  public void testGrantTypeMethods() {
    PermissionView view = new PermissionView();
    view.setGrantType(GrantType.DENY);

    assert view.getGrantType() == GrantType.DENY;
  }

  @Test
  public void testResourceMethods() {
    PermissionView view = new PermissionView();
    view.setResource(Resources.USERS);

    assert view.getResource() == Resources.USERS;
  }

  @Test
    public void testActionMethods() {
    PermissionView view = new PermissionView();
        view.setAction(Actions.READ);

        assert view.getAction() == Actions.READ;
    }

    @Test
    public void testAssociatedUserIdMethods() {
      PermissionView view = new PermissionView();

        UUID id = UUID.randomUUID();
        PermissionViewId adminId = new PermissionViewId();
        adminId.setUserId(id);
        view.setId(adminId);

        assert view.getAssociatedUserId() == id;
    }

}

package com.finalproject.backend.PermissionTests.AdminTests.EntityTests;

import com.finalproject.backend.permissions.admin.entities.AdminPermissionView;
import com.finalproject.backend.permissions.admin.entities.ids.AdminPermissionId;
import com.finalproject.backend.permissions.types.Actions;
import com.finalproject.backend.permissions.types.GrantType;
import com.finalproject.backend.permissions.types.Resources;
import org.junit.jupiter.api.Test;

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

}

package backend.PermissionTests.TypesTests;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.permissions.types.AdminViewTypes;
import backend.permissions.types.ViewTypes;
import org.junit.jupiter.api.Test;

public class AdminViewTypesTests {

  @Test
  public void testAssignmentToViewType() {
    ViewTypes view = AdminViewTypes.PERMISSIONS;
    assertNotNull(view);
    assertInstanceOf(AdminViewTypes.class, view);
  }

  @Test
  public void testGetViewTypeName() {
    ViewTypes view = AdminViewTypes.PERMISSIONS;
    assert view.getViewTypeName().equals("PERMISSIONS");
  }
}

package backend.PermissionTests.TypesTests;


import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.permissions.types.UserViewType;
import backend.permissions.types.ViewTypes;
import org.junit.jupiter.api.Test;

public class UserViewTypesTests {

  @Test
  public void testAssignmentToViewType() {
    ViewTypes view = UserViewType.USER;
    assertNotNull(view);
    assertInstanceOf(UserViewType.class, view);
  }

  @Test
  public void testGetViewTypeName() {
    ViewTypes view = UserViewType.USER;
    assert view.getViewTypeName().equals("USER");
  }
}

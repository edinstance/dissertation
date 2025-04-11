package com.finalproject.backend.PermissionTests.TypesTests;


import com.finalproject.backend.permissions.types.UserViewType;
import com.finalproject.backend.permissions.types.ViewTypes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

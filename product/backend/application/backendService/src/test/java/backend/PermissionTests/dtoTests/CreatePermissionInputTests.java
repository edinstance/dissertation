package backend.PermissionTests.dtoTests;

import backend.permissions.dto.CreatePermissionInput;
import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreatePermissionInputTests {

  private CreatePermissionInput createPermissionInput;

  @Test
  public void testDefaultConstructor() {
    createPermissionInput = new CreatePermissionInput();
    assertNotNull(createPermissionInput);
  }

  @Test
  public void testConstructorWithParameters() {
    createPermissionInput = new CreatePermissionInput(
            "Test Permission",
            Actions.READ,
            "Test Action Description",
            Resources.USERS,
            "Test Resource Description"
    );
    assertNotNull(createPermissionInput);

    assertEquals("Test Permission", createPermissionInput.getPermissionDescription());
    assertEquals("Test Action Description", createPermissionInput.getActionDescription());
    assertEquals("Test Resource Description", createPermissionInput.getResourceDescription());
    assertEquals(Actions.READ, createPermissionInput.getAction());
    assertEquals(Resources.USERS, createPermissionInput.getResource());
  }

  @BeforeEach
  public void setUp() {
    createPermissionInput = new CreatePermissionInput(
            "Test Permission",
            Actions.READ,
            "Test Action Description",
            Resources.USERS,
            "Test Resource Description"
    );
  }

  @Test
  public void testPermissionDescriptionMethods() {
    createPermissionInput.setPermissionDescription("New Test Permission Description");
    assertEquals("New Test Permission Description", createPermissionInput.getPermissionDescription());
  }

  @Test
  public void testActionMethods() {
    createPermissionInput.setAction(Actions.WRITE);
    assertEquals(Actions.WRITE, createPermissionInput.getAction());
  }

  @Test
  public void testActionDescriptionMethods() {
    createPermissionInput.setActionDescription("New Test Action Description");
    assertEquals("New Test Action Description", createPermissionInput.getActionDescription());
  }

  @Test
  public void testResourceMethods() {
    createPermissionInput.setResource(Resources.ADMIN_PERMISSIONS);
    assertEquals(Resources.ADMIN_PERMISSIONS, createPermissionInput.getResource());
  }

  @Test
  public void testResourceDescriptionMethods() {
    createPermissionInput.setResourceDescription("New Test Resource Description");
    assertEquals("New Test Resource Description", createPermissionInput.getResourceDescription());
  }

}

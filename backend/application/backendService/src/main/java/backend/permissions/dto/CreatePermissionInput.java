package backend.permissions.dto;

import backend.permissions.types.Actions;
import backend.permissions.types.Resources;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the input for creating a permission.
 */
@Getter
@Setter
public class CreatePermissionInput {

  /**
   * The description of the permission.
   */
  private String permissionDescription;

  /**
   * The action associated with the permission.
   */
  private Actions action;

  /**
   * The description of the action.
   */
  private String actionDescription;

  /**
   * The resource associated with the permission.
   */
  private Resources resource;

  /**
   * The description of the resource.
   */
  private String resourceDescription;

  /**
   * Default constructor.
   */
  public CreatePermissionInput() {
  }

  /**
   * Constructs a CreatePermissionInput with the specified parameters.
   *
   * @param permissionDescription the description of the permission.
   * @param action                the action associated with the permission.
   * @param actionDescription     the description of the action.
   * @param resource              the resource associated with the permission.
   * @param resourceDescription   the description of the resource.
   */
  public CreatePermissionInput(String permissionDescription,
                               Actions action,
                               String actionDescription,
                               Resources resource,
                               String resourceDescription) {
    this.permissionDescription = permissionDescription;
    this.action = action;
    this.actionDescription = actionDescription;
    this.resource = resource;
    this.resourceDescription = resourceDescription;
  }
}

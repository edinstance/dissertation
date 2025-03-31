package com.finalproject.backend.permissions.types;

/**
 * The admin view types.
 */
public enum AdminViewTypes implements ViewTypes {
  ALL,
  PERMISSIONS,
  ROLES;

  /**
   * Gets the view type name.
   *
   * @return the view type name.
   */
  @Override
  public String getViewTypeName() {
    return this.name();
  }
}
package com.finalproject.backend.permissions.types;

/**
 * The user view types.
 */
public enum UserViewType implements ViewTypes {
  USER;

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

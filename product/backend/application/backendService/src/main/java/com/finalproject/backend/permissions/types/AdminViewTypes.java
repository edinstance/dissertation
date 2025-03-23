package com.finalproject.backend.permissions.types;

public enum AdminViewTypes implements ViewTypes {
  ALL,
  PERMISSIONS,
  ROLES;

  @Override
  public String getViewTypeName() {
    return this.name();
  }
}
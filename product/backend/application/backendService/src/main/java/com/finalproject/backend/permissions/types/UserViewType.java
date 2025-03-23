package com.finalproject.backend.permissions.types;

public enum UserViewType implements ViewTypes{
  USER;

  @Override
  public String getViewTypeName() {
    return this.name();
  }
}

package com.finalproject.backend.users.dto;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user input.
 */
@Getter
@Setter
public class UserInput {

  /**
   * The Users id.
   */
  private UUID id;

  /**
   * The Users email.
   */
  private String email;

  /**
   * The Users name.
   */
  private String name;

  /**
   * Default constructor.
   */
  public UserInput() {
  }

  /**
   * This constructor creates a UserInput with the specified details.
   *
   * @param inputId    The user's id.
   * @param inputEmail The user's email.
   * @param inputName  The user's name.
   */
  public UserInput(final UUID inputId, final String inputEmail,
                   final String inputName) {
    this.id = inputId;
    this.email = inputEmail;
    this.name = inputName;
  }
}

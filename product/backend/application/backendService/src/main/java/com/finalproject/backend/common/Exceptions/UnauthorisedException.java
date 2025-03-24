package com.finalproject.backend.common.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a user attempts to access a resource or perform an action
 * they are not authorized to access.
 */
public class UnauthorisedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -1836381265273691330L;

  /**
   * Constructs a new UnauthorisedException with no message.
   */
  public UnauthorisedException() {
    super();
  }

  /**
   * Constructs a new UnauthorisedException with the specified message.
   *
   * @param message the message
   */
  public UnauthorisedException(String message) {
    super(message);
  }

  /**
   * Constructs a new UnauthorisedException with the specified message and cause.
   *
   * @param message the message
   * @param cause the cause of the exception
   */
  public UnauthorisedException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new UnauthorisedException with the specified cause.
   *
   * @param cause the cause of the exception
   */
  public UnauthorisedException(Throwable cause) {
    super(cause);
  }
}
package com.finalproject.backend.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * DeleteResponse class is used to return the response of a delete operation.
 */
@Getter
@Setter
public class DeleteResponse {

  /**
   * success is a boolean that indicates if the delete operation was successful.
   */
  private boolean success;

  /**
   * message is a string that contains the message of the delete operation.
   */
  private String message;

  /**
   * This constructor is used to create a DeleteResponse object.
   *
   * @param success a boolean that indicates if the delete operation was successful.
   * @param message a string that contains the message of the delete operation.
   */
  public DeleteResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}

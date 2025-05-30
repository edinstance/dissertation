package backend.common.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * MutationResponse class is used to return the response of a mutation.
 */
@Getter
@Setter
public class MutationResponse {

  /**
   * success is a boolean that indicates if the mutation was successful.
   */
  private boolean success;

  /**
   * message is a string that contains the message of the mutation.
   */
  private String message;

  /**
   * This constructor is used to create a MutationResponse object.
   *
   * @param success a boolean that indicates if the mutation was successful.
   * @param message a string that contains the message of the mutation.
   */
  public MutationResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}

package backend.users.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for user details input.
 */
@Getter
@Setter
public class UserDetailsInput {

  /**
   * The users contact Number.
   */
  private String contactNumber;

  /**
   * The users house name.
   */
  private String houseName;

  /**
   * The users street.
   */
  private String addressStreet;

  /**
   * The users city.
   */
  private String addressCity;

  /**
   * The users county.
   */
  private String addressCounty;

  /**
   * The user's postcode.
   */
  private String addressPostcode;

  /**
   * Default constructor.
   */
  public UserDetailsInput() {
  }

  /**
   * This constructor creates a UserDetailsInput with the specified details.
   *
   * @param contactNumber   The user's contact number.
   * @param houseName       The user's house name.
   * @param addressStreet   The user's street address.
   * @param addressCity     The user's city.
   * @param addressCounty   The user's county.
   * @param addressPostcode The user's postal code.
   */
  public UserDetailsInput(final String contactNumber,
                          final String houseName, final String addressStreet,
                        final String addressCity, final String addressCounty,
                        final String addressPostcode) {
    this.contactNumber = contactNumber;
    this.houseName = houseName;
    this.addressStreet = addressStreet;
    this.addressCity = addressCity;
    this.addressCounty = addressCounty;
    this.addressPostcode = addressPostcode;
  }
}

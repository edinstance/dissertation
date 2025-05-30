package backend.users.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents user details entity with additional information.
 */
@Entity
@Getter
@Setter
@Table(name = "user_details")
public class UserDetailsEntity {

  /**
   * The user's id.
   */
  @Id
  @Column(unique = true, nullable = false, name = "user_id")
  private UUID id;

  /**
   * The user's contact number.
   */
  @Column(name = "contact_number")
  private String contactNumber;

  /**
   * The user's house name or number.
   */
  @Column(name = "house_name")
  private String houseName;

  /**
   * The user's street address.
   */
  @Column(name = "address_street")
  private String addressStreet;

  /**
   * The user's city.
   */
  @Column(name = "address_city")
  private String addressCity;

  /**
   * The user's county.
   */
  @Column(name = "address_county")
  private String addressCounty;

  /**
   * The user's postal code.
   */
  @Column(name = "address_post_code")
  private String addressPostcode;

  /**
   * Default constructor.
   */
  public UserDetailsEntity() {
  }

  /**
   * This constructor creates a new UserDetailsEntity with specified details.
   *
   * @param id              The user's id.
   * @param contactNumber   The user's contact number.
   * @param houseName       The user's house name.
   * @param addressStreet   The user's street address.
   * @param addressCity     The user's city.
   * @param addressCounty   The user's county.
   * @param addressPostcode The user's postal code.
   */
  public UserDetailsEntity(final UUID id,
                           final String contactNumber,
                           final String houseName,
                           final String addressStreet,
                           final String addressCity,
                           final String addressCounty,
                           final String addressPostcode) {
    this.id = id;
    this.contactNumber = contactNumber;
    this.houseName = houseName;
    this.addressStreet = addressStreet;
    this.addressCity = addressCity;
    this.addressCounty = addressCounty;
    this.addressPostcode = addressPostcode;
  }

  @Override
  public String toString() {
    return "UserDetailsEntity{"
            + "id=" + id
            + ", contactNumber='" + contactNumber
            + '\'' + ", houseName='" + houseName
            + '\'' + ", addressStreet='" + addressStreet
            + '\'' + ", addressCity='" + addressCity
            + '\'' + ", addressCounty='" + addressCounty
            + '\'' + ", addressPostcode='" + addressPostcode
            + '\'' + '}';
  }
}
package com.finalproject.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user entity with basic information.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
@JsonIgnoreProperties({"items"})
public class UserEntity {

  /**
   * The user's id.
   */
  @Id
  @Column(unique = true, nullable = false, name = "user_id")
  private UUID id;

  /**
   * The users name.
   */
  @Column(name = "email")
  private String email;

  /**
   * The users email.
   */
  @Column(name = "name")
  private String name;

  /**
   * The status of the user.
   */
  @Column(name = "status")
  private String status = "PENDING";

  /**
   * If the user is deleted or not.
   */
  @Column(name = "is_deleted")
  private Boolean isDeleted = false;

  /**
   * The user's items.
   */
  @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemEntity> items;

  /**
  * The users detail's.
  */
  @OneToOne
  @JoinColumn(name = "user_id")
  private UserDetailsEntity userDetailsEntity;

  /**
   * Default constructor.
   */
  public UserEntity() {
  }

  /**
   * This constructor creates a new UserEntity with specified details.
   *
   * @param inputId    The user's id.
   * @param inputEmail The user's email.
   * @param inputName  The user's name.
   */
  public UserEntity(final UUID inputId,
                    final String inputEmail, final String inputName) {
    this.id = inputId;
    this.email = inputEmail;
    this.name = inputName;
  }

  /**
   * This constructor includes a user status.
   *
   * @param inputId     The user's id.
   * @param inputEmail  The user's email.
   * @param inputName   The user's name.
   * @param inputStatus The user's status.
   */
  public UserEntity(final UUID inputId,
                    final String inputEmail, final String inputName,
                    final String inputStatus) {
    this.id = inputId;
    this.email = inputEmail;
    this.name = inputName;
    this.status = inputStatus;
  }

  @Override
  public String toString() {
    return "UserEntity{"
            + "id=" + id
            + ", email='" + email
            + '\'' + ", name='" + name
            + '\'' + ", status='" + status
            + '\'' + ", userDetailsEntity=" + (userDetailsEntity != null
            ? userDetailsEntity.toString() : "null")
            + '}';
  }

}

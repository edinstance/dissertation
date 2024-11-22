package com.finalproject.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a user entity with basic information.
 */
@Entity
@Getter
@Setter
@Table(name = "users")
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
     */
    @Column(name = "status")
    private String status = "PENDING";

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
     * @param inputId    The user's id.
     * @param inputEmail The user's email.
     * @param inputName  The user's name.
     * @param inputStatus The user's status.
     */
    public UserEntity(final UUID inputId,
                      final String inputEmail, final String inputName,
                      final String inputStatus ) {
        this.id = inputId;
        this.email = inputEmail;
        this.name = inputName;
        this.status = inputStatus;
    }


}

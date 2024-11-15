package com.finalproject.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Id
    @Column(unique = true, nullable = false, name = "user_id")
    private UUID id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;

    /**
     * Default constructor.
     */
    public UserEntity() {
    }

    /**
     * This constructor creates a new UserEntity with a specified id, email and name.
     *
     * @param id    The user's id.
     * @param email The user's email.
     * @param name  The user's name.
     */
    public UserEntity(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }


}

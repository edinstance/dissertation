package com.finalproject.backend.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Data Transfer Object for user input.
 */
@Getter
@Setter
public class UserInput {
    private UUID id;
    private String email;
    private String name;

    /**
     * Default constructor.
     */
    public UserInput() {
    }

    /**
     * This constructor creates a new UserInput with a specified id, email and name.
     *
     * @param id    The user's id.
     * @param email The user's email.
     * @param name  The user's name.
     */
    public UserInput(UUID id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}

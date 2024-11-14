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


}

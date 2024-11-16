package com.finalproject.backend.services;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a UserService with the specified UserRepository.
     *
     * @param userRepository The repository for accessing User entities.
     */
    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user.
     *
     * @param newUser the user to be created.
     * @return the created user.
     * @throws IllegalArgumentException an error if a user with the uuid already exists.
     */
    public UserEntity createUser(final UserEntity newUser) {
        Optional<UserEntity> existingUser = userRepository.findById(newUser.getId());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with UUID " + newUser.getId() + " already exists.");
        }
        return userRepository.save(newUser);
    }
}

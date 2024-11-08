package com.finalproject.backend.services;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     */
    public UserEntity createUser(final UserEntity newUser) {
        return userRepository.save(newUser);
    }
}

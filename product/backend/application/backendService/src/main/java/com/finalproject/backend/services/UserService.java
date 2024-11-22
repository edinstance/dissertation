package com.finalproject.backend.services;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities.
 */
@Service
public class UserService {

  /**
   * Repository for accessing user data.
   */
  private final UserRepository userRepository;

  /**
   * Constructs a UserService with the specified UserRepository.
   *
   * @param inputUserRepository The repository for accessing User entities.
   */
  @Autowired
  public UserService(final UserRepository inputUserRepository) {
    this.userRepository = inputUserRepository;
  }

  /**
   * Creates a new user.
   *
   * @param newUser the user to be created.
   * @return the created user.
   * @throws IllegalArgumentException error if duplicated UUId.
   */
  public UserEntity createUser(final UserEntity newUser) {
    Optional<UserEntity> existingUser =
            userRepository.findById(newUser.getId());

    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("User with UUID "
              + newUser.getId() + " already exists.");
    }
    return userRepository.save(newUser);
  }
}

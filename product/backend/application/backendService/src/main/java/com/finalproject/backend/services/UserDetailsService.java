package com.finalproject.backend.services;

import com.finalproject.backend.entities.UserDetailsEntity;
import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.helpers.UserHelpers;
import com.finalproject.backend.repositories.UserDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing User entities.
 */
@Service
public class UserDetailsService {

  /**
   * Repository for accessing user details data.
   */
  private final UserDetailsRepository userDetailsRepository;

  /**
   * User helpers.
   */
  private final UserHelpers userHelpers;

  /**
   * Constructs a UserService with the specified UserRepository and UserDetails Repository.
   *
   * @param inputUserDetailsRepository The repository for accessing User details entities.
   * @param inputUserHelpers The userHelper for this service.
   *
   */
  @Autowired
  public UserDetailsService(final UserDetailsRepository inputUserDetailsRepository,
                            UserHelpers inputUserHelpers) {
    this.userDetailsRepository = inputUserDetailsRepository;
    this.userHelpers = inputUserHelpers;
  }

  /**
   * Updates or creates the user details.
   *
   * @param newDetails the details to be edited.
   * @throws IllegalArgumentException error if duplicated UUId.
   */
  @Transactional
  public UserEntity saveUserDetails(final UserDetailsEntity newDetails) {
    userDetailsRepository.saveUserDetails(newDetails.getId(),
            newDetails.getContactNumber(), newDetails.getAddressStreet(),
            newDetails.getAddressCity(), newDetails.getAddressCounty(),
            newDetails.getAddressPostcode()
    );
    UserEntity user = userHelpers.getUserById(newDetails.getId());
    user.setUserDetailsEntity(newDetails);
    return user;
  }

}
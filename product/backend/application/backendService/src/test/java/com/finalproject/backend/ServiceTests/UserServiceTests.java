package com.finalproject.backend.ServiceTests;

import com.finalproject.backend.entities.UserEntity;
import com.finalproject.backend.repositories.UserRepository;
import com.finalproject.backend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateExistingUser() {
        UUID userId = UUID.randomUUID();
        UserEntity existingUser = new UserEntity(userId, "existing@test.com", "Existing User");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(newUser),
                "User with UUID " + newUser.getId() + " already exists.");
    }

    @Test
    public void testCreateNewUser() {
        UUID userId = UUID.randomUUID();
        UserEntity newUser = new UserEntity(userId, "new@test.com", "New User");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        UserEntity savedUser = userService.createUser(newUser);

        assertEquals(newUser, savedUser);
        assert savedUser.getStatus().equals("PENDING");
    }
}

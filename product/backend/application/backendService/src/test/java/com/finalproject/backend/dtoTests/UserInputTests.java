package com.finalproject.backend.dtoTests;

import com.finalproject.backend.dto.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserInputTests {

    private UserInput userInput;

    @Test
    public void testDefaultConstructor() {
        userInput = new UserInput();

        assertNotNull(userInput);
    }

    @Test
    public void testUserInputConstructor() {
        userInput = new UserInput(UUID.randomUUID(), "test@test.com", "name");

        assertNotNull(userInput);
        assert userInput.getEmail().equals("test@test.com");
        assert userInput.getName().equals("name");
    }

    @BeforeEach
    public void setUp() {
        userInput = new UserInput(UUID.randomUUID(), "test@test.com", "name");
    }

    @Test
    public void testIdMethods() {
        UUID newUserId = UUID.randomUUID();
        userInput.setId(newUserId);
        assert userInput.getId().equals(newUserId);
    }

    @Test
    public void testEmailMethods() {
        userInput.setEmail("test2@test.com");
        assert userInput.getEmail().equals("test2@test.com");
    }

    @Test
    public void testNameMethods() {
        userInput.setName("name2");
        assert userInput.getName().equals("name2");
    }

}

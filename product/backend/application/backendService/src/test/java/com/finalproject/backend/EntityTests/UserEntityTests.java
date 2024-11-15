package com.finalproject.backend.EntityTests;

import com.finalproject.backend.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserEntityTests {

    private UserEntity userEntity;

    @Test
    public void testDefaultConstructor() {
        userEntity = new UserEntity();

        assertNotNull(userEntity);
    }

    @Test
    public void testConstructor() {
        userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name");

        assertNotNull(userEntity);
        assertNotNull(userEntity.getId());
        assert userEntity.getEmail().equals("test@test.com");
        assert userEntity.getName().equals("name");
    }

    @BeforeEach
    public void setUp() {
        userEntity = new UserEntity(UUID.randomUUID(), "test@test.com", "name");
    }

    @Test
    public void testIdMethods() {
        UUID userId = UUID.randomUUID();
        userEntity.setId(userId);
        assert userEntity.getId().equals(userId);
    }

    @Test
    public void testEmailMethods() {
        userEntity.setEmail("test2@test.com");
        assert userEntity.getEmail().equals("test2@test.com");
    }

    @Test
    public void testNameMethods() {
        userEntity.setName("name2");
        assert userEntity.getName().equals("name2");
    }
}
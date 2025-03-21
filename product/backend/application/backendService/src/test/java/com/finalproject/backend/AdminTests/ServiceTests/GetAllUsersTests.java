package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.users.entities.UserEntity;
import com.finalproject.backend.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class GetAllUsersTests {


  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AdminService adminService;


  @Test
  public void getAllUsersNoResultsTest(){
    when(userRepository.findAll()).thenReturn(List.of());

    List<UserEntity> result = adminService.getAllUsers();

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(userRepository, times(1)).findAll();
  }

  @Test
  public void getAllUsersTest(){

    UserEntity userEntity = new UserEntity(UUID.randomUUID(), "email@test.com", "name");
    when(userRepository.findAll()).thenReturn(List.of(userEntity));

    List<UserEntity> result = adminService.getAllUsers();

    assertNotNull(result);
    assertTrue(result.contains(userEntity));
    verify(userRepository, times(1)).findAll();
  }
}
package com.finalproject.backend.AdminTests.ServiceTests;

import com.finalproject.backend.admin.dto.UserStats;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.users.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserStatsTests {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private AdminService adminService;

  @Test
  public void getUserStatsWithData() {
    List<Object[]> mockResults = new ArrayList<>();
    Object[] row = {10L, 5L, 2L};
    mockResults.add(row);

    when(userRepository.getAdminUserStats()).thenReturn(mockResults);

    UserStats userStats = adminService.getUserStats();

    assertEquals(10, userStats.getTotal());
    assertEquals(5, userStats.getNewUserTotal());
    assertEquals(2, userStats.getDeletedUserTotal());
  }

  @Test
  public void getUserStatsEmptyData() {
    when(userRepository.getAdminUserStats()).thenReturn(new ArrayList<>());

    UserStats userStats = adminService.getUserStats();

    assertEquals(0, userStats.getTotal());
    assertEquals(0, userStats.getNewUserTotal());
    assertEquals(0, userStats.getDeletedUserTotal());
  }

  @Test
  public void getUserStatsNullResults() {
    when(userRepository.getAdminUserStats()).thenReturn(null);

    UserStats userStats = adminService.getUserStats();

    assertEquals(0, userStats.getTotal());
    assertEquals(0, userStats.getNewUserTotal());
    assertEquals(0, userStats.getDeletedUserTotal());
  }
}

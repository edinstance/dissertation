package com.finalproject.backend.AdminTests.MutationTests;

import com.finalproject.backend.admin.mutations.AdminMutations;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.dto.MutationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AdminMutationTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminMutations adminMutations;

  @Test
    public void createAdminTest() {
    UUID userId = UUID.randomUUID();

    MutationResponse result = adminMutations.createAdmin(userId.toString());

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Admin created successfully");
  }
}

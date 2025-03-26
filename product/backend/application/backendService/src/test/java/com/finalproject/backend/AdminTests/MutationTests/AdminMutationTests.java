package com.finalproject.backend.AdminTests.MutationTests;

import com.finalproject.backend.admin.mutations.AdminMutations;
import com.finalproject.backend.admin.services.AdminService;
import com.finalproject.backend.common.dto.MutationResponse;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

  @Test
  public void promoteAdminToSuperAdminTest() {
    UUID userId = UUID.randomUUID();

    MutationResponse result = adminMutations.promoteAdminToSuperAdmin(userId.toString());

    assertTrue(result.isSuccess());
    assert result.getMessage().equals("Admin promoted successfully");
  }
}

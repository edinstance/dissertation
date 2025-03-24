package com.finalproject.backend.AdminTests.MutationTests;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.mutations.AdminMutations;
import com.finalproject.backend.admin.queries.AdminQueries;
import com.finalproject.backend.admin.services.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminMutationTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminMutations adminMutations;

  @Test
    public void createAdminTest() {
    UUID userId = UUID.randomUUID();
    AdminEntity adminEntity = new AdminEntity(userId, false, "Active", userId, userId );
    when(adminService.createAdmin(any(UUID.class))).thenReturn(adminEntity);

    AdminEntity result = adminMutations.createAdmin(userId.toString());

    assert adminEntity.equals(result);
  }
}

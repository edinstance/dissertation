package com.finalproject.backend.AdminTests.QueryTests;

import com.finalproject.backend.admin.entities.AdminEntity;
import com.finalproject.backend.admin.queries.AdminQueries;
import com.finalproject.backend.admin.services.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GetAllAdminsTests {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminQueries adminQueries;

  @Test
  void testGetAllUsers() {
    UUID adminId = UUID.randomUUID();
    AdminEntity adminEntity = new AdminEntity(adminId, false, "Active", adminId, adminId);

    when(adminService.getAllAdmins()).thenReturn(List.of(adminEntity));

    List<AdminEntity> admins = adminQueries.getAllAdmins();

    assertNotNull(admins);
    assert admins.size() == 1;
    assert admins.contains(adminEntity);
  }
}

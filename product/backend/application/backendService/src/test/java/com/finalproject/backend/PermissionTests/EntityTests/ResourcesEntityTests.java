package com.finalproject.backend.PermissionTests.EntityTests;

import com.finalproject.backend.permissions.entities.ResourcesEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourcesEntityTests {

  private static ResourcesEntity resourcesEntity;

  @Test
  public void testDefaultConstructor() {
    resourcesEntity = new ResourcesEntity();
    assertNotNull(resourcesEntity);
  }

  @Test
  public void testConstructor() {
    UUID id = UUID.randomUUID();
    resourcesEntity = new ResourcesEntity(id, "Resource", "Description");
    assertNotNull(resourcesEntity);
    assert resourcesEntity.getId().equals(id);
    assert resourcesEntity.getResource().equals("Resource");
    assert resourcesEntity.getDescription().equals("Description");
  }

  @BeforeAll
  public static void setUp() {
    resourcesEntity = new ResourcesEntity(UUID.randomUUID(), "Resource", "Description");
  }

  @Test
  public void testIdMethods(){
    UUID newId = UUID.randomUUID();
    resourcesEntity.setId(newId);
    assert resourcesEntity.getId().equals(newId);
  }

  @Test
  public void testResourceMethods(){
    resourcesEntity.setResource("New Resource");
    assert resourcesEntity.getResource().equals("New Resource");
  }

  @Test
  public void testDescriptionMethods(){
    resourcesEntity.setDescription("New Description");
    assert resourcesEntity.getDescription().equals("New Description");
  }

}

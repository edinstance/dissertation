package com.finalproject.backend.PermissionTests.EntityTests;

import com.finalproject.backend.permissions.entities.ActionsEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActionsEntityTests {

  private static ActionsEntity actionsEntity;

  @Test
  public void testDefaultConstructor(){
    actionsEntity = new ActionsEntity();
    assertNotNull(actionsEntity);
  }

  @Test
  public void testConstructor(){
    actionsEntity = new ActionsEntity(UUID.randomUUID(), "Action", "Description");
    assertNotNull(actionsEntity);
    assert actionsEntity.getAction().equals("Action");
    assert actionsEntity.getDescription().equals("Description");
  }

  @BeforeAll
  public static void setUp(){
    actionsEntity = new ActionsEntity(UUID.randomUUID(), "Action", "Description");
  }

  @Test
  public void testActionIdMethods(){
    UUID newActionId = UUID.randomUUID();
    actionsEntity.setActionId(newActionId);
    assert(actionsEntity.getActionId().equals(newActionId));
  }

  @Test
  public void testActionMethods(){
    actionsEntity.setAction("New Action");
    assert(actionsEntity.getAction().equals("New Action"));
  }

  @Test
  public void testDescriptionMethods(){
    actionsEntity.setDescription("New Description");
    assert(actionsEntity.getDescription().equals("New Description"));
  }
}

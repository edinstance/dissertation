package backend.PermissionTests.EntityTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import backend.permissions.entities.ActionsEntity;
import backend.permissions.types.Actions;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ActionsEntityTests {

  private static ActionsEntity actionsEntity;

  @Test
  public void testDefaultConstructor() {
    actionsEntity = new ActionsEntity();
    assertNotNull(actionsEntity);
  }

  @Test
  public void testConstructor() {
    actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Description");
    assertNotNull(actionsEntity);
    assert actionsEntity.getAction().equals(Actions.READ);
    assert actionsEntity.getDescription().equals("Description");
  }

  @BeforeAll
  public static void setUp() {
    actionsEntity = new ActionsEntity(UUID.randomUUID(), Actions.READ, "Description");
  }

  @Test
  public void testActionIdMethods() {
    UUID newActionId = UUID.randomUUID();
    actionsEntity.setActionId(newActionId);
    assert (actionsEntity.getActionId().equals(newActionId));
  }

  @Test
  public void testActionMethods() {
    actionsEntity.setAction(Actions.WRITE);
    assert (actionsEntity.getAction().equals(Actions.WRITE));
  }

  @Test
  public void testDescriptionMethods() {
    actionsEntity.setDescription("New Description");
    assert (actionsEntity.getDescription().equals("New Description"));
  }
}

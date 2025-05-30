package backend.permissions.entities;


import backend.permissions.types.Actions;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an action entity.
 */
@Entity
@Getter
@Setter
@Table(name = "actions")
public class ActionsEntity {

  /**
   * The id of the action.
   */
  @Id
  @Column(name = "action_id")
  private UUID actionId;

  /**
   * The action.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "action")
  private Actions action;

  /**
   * The description of the action.
   */
  @Column(name = "description")
  private String description;

  /**
   * Default constructor.
   */
  public ActionsEntity() {
  }

  /**
   * Constructor with options.
   *
   * @param actionId    the id of the action.
   * @param action      the action.
   * @param description the description.
   */
  public ActionsEntity(UUID actionId, Actions action, String description) {
    this.actionId = actionId;
    this.action = action;
    this.description = description;
  }
}
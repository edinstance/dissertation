package com.finalproject.backend.permissions.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @Column(name = "action")
  private String action;

  /**
   * The description of the action.
   */
  @Column(name = "description")
  private String description;

  /**
   * Default constructor.
   */
  public ActionsEntity() {}

  /**
   * Constructor with options.
   *
   * @param actionId the id of the action.
   * @param action the action.
   * @param description the description.
   */
  public ActionsEntity(UUID actionId, String action, String description) {
    this.actionId = actionId;
    this.action = action;
    this.description = description;
  }
}
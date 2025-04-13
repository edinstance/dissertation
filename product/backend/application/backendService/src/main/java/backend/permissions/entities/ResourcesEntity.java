package backend.permissions.entities;


import backend.permissions.types.Resources;
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
 * Represents a resource entity.
 */
@Entity
@Getter
@Setter
@Table(name = "resources")
public class ResourcesEntity {

  /**
   * The id of the resource.
   */
  @Id
  @Column(name = "resource_id")
  private UUID id;

  /**
   * The resource.
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "resource")
  private Resources resource;

  /**
   * The description of the resource.
   */
  @Column(name = "description")
  private String description;

  /**
   * Default constructor.
   */
  public ResourcesEntity() {
  }

  /**
   * Resource entity constructor with options.
   *
   * @param id the id of the resource.
   * @param resource the resource.
   * @param description the description of the resource.
   */
  public ResourcesEntity(UUID id, Resources resource, String description) {
    this.id = id;
    this.resource = resource;
    this.description = description;
  }
}
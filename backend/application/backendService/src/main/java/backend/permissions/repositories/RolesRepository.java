package backend.permissions.repositories;

import backend.permissions.entities.RoleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing roles entities.
 */
public interface RolesRepository extends JpaRepository<RoleEntity, UUID> {
}
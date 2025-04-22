package backend.bids.repositories;

import backend.bids.entities.WinningBidEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing winning bid entities.
 */
public interface WinningBidRepository extends JpaRepository<WinningBidEntity, UUID> {

}

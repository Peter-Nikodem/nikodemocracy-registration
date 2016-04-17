package net.nikodem.repository;

import net.nikodem.model.entity.VoterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Peter Nikodem
 */

public interface VoterRepository extends JpaRepository<VoterEntity,Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN 'true' ELSE 'false' END FROM Voter v WHERE v.username = ?1")
    boolean existsByUsername(String username);
}

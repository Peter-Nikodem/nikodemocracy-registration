package net.nikodem.repository;

import net.nikodem.model.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VoterRepository extends JpaRepository<VoterEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN 'true' ELSE 'false' END FROM Voter v WHERE v.username = ?1")
    boolean existsByUsername(String username);

    Optional<VoterEntity> findByUsername(String username);
}

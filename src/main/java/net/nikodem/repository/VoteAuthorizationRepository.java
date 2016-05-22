package net.nikodem.repository;

import net.nikodem.model.entity.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface VoteAuthorizationRepository extends JpaRepository<VoteAuthorizationEntity, Long> {

    Optional<VoteAuthorizationEntity> findByVoterUsernameAndElectionElectionId(String username, String electionId);

}

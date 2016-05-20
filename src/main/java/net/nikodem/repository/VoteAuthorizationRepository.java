package net.nikodem.repository;

import net.nikodem.model.entity.VoteAuthorizationEntity;
import net.nikodem.model.json.VoteAuthorizationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteAuthorizationRepository extends JpaRepository<VoteAuthorizationEntity, Long> {

    Optional<VoteAuthorizationEntity> findByVoterUsernameAndElectionElectionId(String username, String electionId);

}

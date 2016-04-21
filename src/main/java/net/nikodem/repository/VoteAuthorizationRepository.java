package net.nikodem.repository;

import net.nikodem.model.entity.VoteAuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface VoteAuthorizationRepository extends JpaRepository<VoteAuthorizationEntity,Long> {


}

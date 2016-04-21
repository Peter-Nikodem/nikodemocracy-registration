package net.nikodem.repository;

import net.nikodem.model.entity.ElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface ElectionRepository extends JpaRepository<ElectionEntity,Long>{

}

package net.nikodem.repository;

import net.nikodem.model.entity.ElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<ElectionEntity,Long>{

}

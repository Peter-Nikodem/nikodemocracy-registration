package net.nikodem.repository;

import net.nikodem.model.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Peter Nikodem
 */
public interface AnswerRepository extends JpaRepository<AnswerEntity,Long> {
}

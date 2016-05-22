package net.nikodem.repository;

import net.nikodem.model.entity.*;
import org.springframework.data.jpa.repository.*;

public interface ElectionRepository extends JpaRepository<ElectionEntity, Long> {}

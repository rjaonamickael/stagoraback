package com.stagora.dao.employers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.stagora.entities.employers.Stage;

@Repository
public interface DaoStage  extends JpaRepository<Stage, Long>, JpaSpecificationExecutor<Stage> {
	// Pour une recherche basée sur des critères dynamiques, utiliser Specification.
}

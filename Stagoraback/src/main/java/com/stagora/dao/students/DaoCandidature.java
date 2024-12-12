package com.stagora.dao.students;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stagora.entities.students.Candidature;


public interface DaoCandidature extends JpaRepository<Candidature, Long>{
	
	
	@Query("SELECT c FROM Candidature c where c.stage.id = :x")
	public Page<Candidature> findCandidatureByStage(@Param("x")Long idStage, Pageable pageable);
	
	
	@Query("SELECT c FROM Candidature c JOIN c.stage s WHERE s.employeur.id = :xe")
	public Page<Candidature> findAllCandidatureEmployeur(	@Param("xe") Long idEmployeur, 
																	Pageable pageable);
}
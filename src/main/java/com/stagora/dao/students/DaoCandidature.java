package com.stagora.dao.students;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stagora.entities.students.Candidature;

public interface DaoCandidature extends JpaRepository<Candidature, Long> {

    // Récupérer les candidatures pour un stage spécifique avec pagination
    @Query("SELECT c FROM Candidature c WHERE c.stage.id = :idStage")
    Page<Candidature> findCandidatureByStage(@Param("idStage") Long idStage, Pageable pageable);

    // Récupérer toutes les candidatures pour un employeur avec pagination
    @Query("SELECT c FROM Candidature c JOIN c.stage s WHERE s.employeur.id = :idEmployeur")
    Page<Candidature> findAllCandidatureEmployeur(@Param("idEmployeur") Long idEmployeur, Pageable pageable);

    // Récupérer toutes les candidatures d'un étudiant avec pagination
    @Query("SELECT c FROM Candidature c WHERE c.etudiant.id = :idEtudiant")
    Page<Candidature> findAllByEtudiantId(@Param("idEtudiant") Long idEtudiant, Pageable pageable);
}

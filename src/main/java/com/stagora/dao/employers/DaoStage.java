package com.stagora.dao.employers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stagora.entities.employers.Stage;
import com.stagora.utils.employeur.ModaliteStage;

@Repository
public interface DaoStage extends JpaRepository<Stage, Long>, JpaSpecificationExecutor<Stage> {

    // Ajout d'une méthode de recherche basée sur des critères dynamiques avec une requête JPQL
    @Query("SELECT s FROM Stage s WHERE " +
           "(:intitule IS NULL OR s.intitule LIKE %:intitule%) AND " +
           "(:categorie IS NULL OR s.categorie LIKE %:categorie%) AND " +
           "(:modalite IS NULL OR s.modalite = :modalite) AND " +
           "(:adresse IS NULL OR s.adresse LIKE %:adresse%)")
    List<Stage> findStagesByFilters(@Param("intitule") String intitule,
                                    @Param("categorie") String categorie,
                                    @Param("modalite") ModaliteStage modalite,
                                    @Param("adresse") String adresse);
}

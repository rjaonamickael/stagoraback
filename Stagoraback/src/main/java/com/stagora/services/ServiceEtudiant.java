package com.stagora.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoStage;
import com.stagora.dao.students.DaoCandidature;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dto.DtoCandidature;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Candidature;
import com.stagora.entities.students.Etudiant;
import com.stagora.utils.employeur.ModaliteStage;
import com.stagora.utils.etudiant.EtatCandidature;
import com.stagora.utils.etudiant.SpecificationRechercheStage;
import java.util.NoSuchElementException;

@Service
public class ServiceEtudiant {
	
	@Autowired
	private DaoEtudiant daoEtudiant;
	
	@Autowired
	private DaoStage daoStage;
	
	@Autowired
	private DaoCandidature daoCandidature;
	
	
	public ResponseEntity<DtoCandidature> addCandidature(DtoCandidature dtoCandidature) {
	    try {
	        System.out.println("Données reçues pour la candidature : " + dtoCandidature);

	        // Récupération de l'étudiant
	        Etudiant etudiant = daoEtudiant.findById(dtoCandidature.getId_etudiant())
	                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'ID : " + dtoCandidature.getId_etudiant()));
	        System.out.println("Étudiant trouvé : " + etudiant);

	        // Récupération du stage
	        Stage stage = daoStage.findById(dtoCandidature.getId_stage())
	                .orElseThrow(() -> new RuntimeException("Stage introuvable avec l'ID : " + dtoCandidature.getId_stage()));
	        System.out.println("Stage trouvé : " + stage);

	        // Création de la candidature
	        Candidature candidature = new Candidature();
	        candidature.setDate_candidature(dtoCandidature.getDate_candidature());
	        candidature.setCv(dtoCandidature.getCv());
	        candidature.setLettreMotivation(dtoCandidature.getLettreMotivation());
	        candidature.setEtudiant(etudiant);
	        candidature.setStage(stage);

	        // Sauvegarde de la candidature
	        daoCandidature.save(candidature);

	        return ResponseEntity.status(HttpStatus.CREATED).body(DtoCandidature.toDTOCandidature(candidature));
	    } catch (RuntimeException e) {
	        System.err.println("Erreur lors de l'ajout de la candidature : " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    } catch (Exception e) {
	        System.err.println("Erreur inattendue lors de l'ajout de la candidature : " + e.getMessage());
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}





	
	public ResponseEntity<List<Stage> > getStagesFiltre(String intitule, String categorie, 
														ModaliteStage modalite, String adresse) {
		
		// Construire la spécification avec les critères fournis
        Specification<Stage> spec = SpecificationRechercheStage.filtre(intitule, categorie, modalite, adresse);
        
        
        // Rechercher les stages correspondants
		List<Stage> stages = daoStage.findAll(spec);
		
		return ResponseEntity.status(HttpStatus.OK).body(stages);
	
	}
	
}

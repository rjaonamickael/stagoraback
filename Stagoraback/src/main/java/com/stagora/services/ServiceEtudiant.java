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

@Service
public class ServiceEtudiant {
	
	@Autowired
	private DaoEtudiant daoEtudiant;
	
	@Autowired
	private DaoStage daoStage;
	
	@Autowired
	private DaoCandidature daoCandidature;
	
	
	public ResponseEntity<DtoCandidature> addCandidature(DtoCandidature dtoCandidature) {
		
		// Creation de la candidature
		Candidature candidature = new Candidature();
		
		
		
		candidature.setDate_candidature(dtoCandidature.getDate_candidature());
		
		candidature.setCv(dtoCandidature.getCv());
		
		candidature.setLettreMotivation(dtoCandidature.getLettreMotivation());
		
		candidature.setEtudiant(daoEtudiant.findById(dtoCandidature.getId_etudiant()).get());
		
		candidature.setStage(daoStage.findById(dtoCandidature.getId_stage()).get());
		
		
		daoCandidature.save(candidature);	
		
		
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(DtoCandidature.toDTOCandidature(candidature));
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

package com.stagora.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoStage;
import com.stagora.dao.students.DaoCandidature;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dto.DtoCandidature;
import com.stagora.entities.students.Candidature;
import com.stagora.entities.students.Etudiant;
import com.stagora.utils.etudiant.EtatCandidature;

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
}

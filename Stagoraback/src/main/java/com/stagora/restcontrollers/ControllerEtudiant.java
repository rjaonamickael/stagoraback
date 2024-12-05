package com.stagora.restcontrollers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dto.DtoCandidature;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Etudiant;
import com.stagora.services.ServiceEtudiant;
import com.stagora.utils.employeur.ModaliteStage;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*") 
@RequestMapping("/etudiant")
public class ControllerEtudiant {
	@Autowired
	private ServiceEtudiant serviceEtudiant;
	
	
	@PostMapping(value="/candidater")
	@Transactional
	public ResponseEntity<DtoCandidature> addStage(	@RequestBody DtoCandidature dtoCandidature) {
		
		return serviceEtudiant.addCandidature(dtoCandidature);
	}
	
	
	@GetMapping(value="/recherche")
	public ResponseEntity<List<Stage>> rechercheStage(
			@RequestParam(required = false) String intitule, 
			@RequestParam(required = false) String categorie, 
			@RequestParam(required = false) ModaliteStage modalite, 
			@RequestParam(required = false) String adresse) {
		
		return serviceEtudiant.getStagesFiltre(intitule, categorie, modalite, adresse); 
	}
	
	// Methode pour récupérer tous les profils étudiants
	@GetMapping(value="/listing")
	public List<Etudiant> getAllEtudiants() {
		
		return serviceEtudiant.getAllEtudiants();
	}
	
	
	
}

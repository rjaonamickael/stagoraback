package com.stagora.restcontrollers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dto.DtoCandidature;
import com.stagora.services.ServiceEtudiant;

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
	
	
	
	
}

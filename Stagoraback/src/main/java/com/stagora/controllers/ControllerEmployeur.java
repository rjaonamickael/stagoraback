package com.stagora.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.entities.employers.Stage;
import com.stagora.services.ServiceEmployeur;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*") 
@RequestMapping("/employeur/{id_employeur}")
public class ControllerEmployeur {
	
	@Autowired
	private ServiceEmployeur serviceEmployeur;
	
	
	// AJOUT D'UN STAGE
	@PostMapping(value="/stages")
	@Transactional
	public ResponseEntity<Map<String, String>> addStage(	@PathVariable Long id_employeur,
															@RequestBody Stage stage) {
		
		return serviceEmployeur.ajoutStage(id_employeur,stage);
	}
	
	@GetMapping(value="/stages")
	//@Transactional   Pas vraiment nécessaire
	public List<Stage> getAllStage(	@PathVariable Long id_employeur) {
		
		return serviceEmployeur.toutStage(id_employeur);
	}
	
	
	@GetMapping(value="/stages/{id}")
	//@Transactional   Pas vraiment nécessaire
	public Stage getStage(@PathVariable Long id_employeur, @PathVariable Long id) {
		
		return serviceEmployeur.unStage(id_employeur,id);
	}
	
	
	@DeleteMapping(value="/stages/{id}")
	//@Transactional
	public ResponseEntity<Map<String, String>> deleteStage(	@PathVariable Long id_employeur, 
															@PathVariable Long id) {
		
		return serviceEmployeur.suppressionStage(id_employeur,id);
	}
	
	
	@PutMapping(value="/stages/{id}")
	//@Transactional
	public Stage updateStage(	@PathVariable Long id, 
															@RequestBody Stage stage) {
		
		return serviceEmployeur.misajourStage(id,stage);
	}

}

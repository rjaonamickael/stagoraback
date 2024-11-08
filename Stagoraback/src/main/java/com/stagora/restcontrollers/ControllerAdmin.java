package com.stagora.restcontrollers;


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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stagora.entities.students.Etablissement;
import com.stagora.services.ServiceAdmin;

@RestController
@CrossOrigin("*") // Pour régler ce problème Access-Control-Allow-Origin
@RequestMapping("/admin")
public class ControllerAdmin {
	
	@Autowired
	private ServiceAdmin serviceAdmin;
	
//	@Autowired
//    private ImageStorageService imageStorageService;
	
	
	// REQUÊTE POUR TOUT CE QUI CONCERNE LES ETABLISSEMENT
	// Tous les établissement
	@GetMapping("/etablissements")
	public List<Etablissement> getAllEtablissement(){
		
		return serviceAdmin.toutEtablissement();
	}
	
	// Un établissement
	@GetMapping("/etablissements/{id}")
	public Etablissement getEtablissement(@PathVariable Long id){
		
		return serviceAdmin.unEtablissement(id);
	}
	
	// Ajout d'un Etablissement
	@PostMapping("/etablissements")
	public ResponseEntity<Map<String, String>> addEtablissement(@RequestBody Etablissement e){
		
		return serviceAdmin.ajoutEtablissement(e);
	}
	
	// Suppression d'établissement
	@DeleteMapping("/etablissements/{id}")
	public ResponseEntity<Map<String, String>> deleteEtablissement(@PathVariable Long id){
		
		return serviceAdmin.suppressionEtablissement(id);

	}
	
	// Modification d'un établissement
	@PutMapping("/etablissements/{id}")
	public ResponseEntity<Map<String, String>> updateEtablissement(	@PathVariable Long id,
																	@RequestBody Etablissement e){
		
		return serviceAdmin.misajourEtablissement(id, e);
	}
	
	@PostMapping(value = "/uploadImage", consumes = "multipart/form-data")
	public String uploadImage(@RequestParam("file") MultipartFile file) {
	    // Logique pour enregistrer le fichier
	    return "Fichier enregistré avec succès";
	}
}

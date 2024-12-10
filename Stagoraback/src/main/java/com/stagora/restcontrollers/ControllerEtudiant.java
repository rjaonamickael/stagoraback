package com.stagora.restcontrollers;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dto.DtoCandidature;
import com.stagora.dto.EtudiantDTO;
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
	
	// Récupérer le profil étudiant via l'id utilisateur
    @GetMapping("/user/{id_user}")
    public ResponseEntity<EtudiantDTO> getProfilByUserId(@PathVariable Long id_user) {
        try {
            Etudiant etudiant = serviceEtudiant.getProfilByUserId(id_user);
            return ResponseEntity.ok(EtudiantDTO.fromEntity(etudiant));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Mettre à jour le profil étudiant via l'id utilisateur
    @PutMapping("/user/{id_user}")
    @Transactional
    public ResponseEntity<Etudiant> updateProfilByUserId(@PathVariable Long id_user, 
                                                         @RequestBody Etudiant etudiant) {
        try {
            return ResponseEntity.ok(serviceEtudiant.updateProfilByUserId(id_user, etudiant));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	// Methode pour récupérer tous les profils étudiants
	@GetMapping(value="/listing")
	public ResponseEntity<Page<Etudiant>> getPaginatedEtudiants(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		
		return serviceEtudiant.getPaginatedEtudiants(page,size);
	}
	
	
	
}

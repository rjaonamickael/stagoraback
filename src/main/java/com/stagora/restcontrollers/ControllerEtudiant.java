package com.stagora.restcontrollers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Ajouter une candidature
    @PostMapping("/{id_etudiant}/candidater")
    @Transactional
    public ResponseEntity<DtoCandidature> addCandidature(@PathVariable Long id_etudiant, 
                                                         @RequestBody DtoCandidature dtoCandidature) {
        return serviceEtudiant.addCandidature(id_etudiant, dtoCandidature);
    }

    // Obtenir toutes les candidatures d'un étudiant avec pagination
    @GetMapping("/{id_etudiant}/candidatures")
    public ResponseEntity<Page<DtoCandidature>> getAllCandidatureByPage(
            @PathVariable Long id_etudiant,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return serviceEtudiant.getAllCandidatureByPage(id_etudiant, page, size);
    }

    // Recherche de stages avec filtres
    @GetMapping("/{id_etudiant}/recherche")
    public ResponseEntity<List<Stage>> rechercheStage(
            @RequestParam(required = false) String intitule,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) ModaliteStage modalite,
            @RequestParam(required = false) String adresse) {
        return serviceEtudiant.getStagesFiltre(intitule, categorie, modalite, adresse);
    }

    // Récupérer le profil étudiant via l'id utilisateur
    @GetMapping("/user/{id_user}")
    public ResponseEntity<Etudiant> getProfilByUserId(@PathVariable Long id_user) {
        try {
            Etudiant etudiant = serviceEtudiant.getProfilByUserId(id_user);
            return ResponseEntity.ok(etudiant);
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
}

package com.stagora.restcontrollers;

import java.util.HashMap; // Pour utiliser HashMap
import java.util.List;
import java.util.Map; // Pour Map

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dao.students.DaoEtudiant; // Importer DaoEtudiant
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

    @Autowired
    private DaoEtudiant daoEtudiant; // Injection de DaoEtudiant

    @PostMapping(value = "/candidater")
    @Transactional
    public ResponseEntity<?> addStage(@RequestBody DtoCandidature dtoCandidature) {
        try {
            System.out.println("Requête reçue : " + dtoCandidature);
            return serviceEtudiant.addCandidature(dtoCandidature);
        } catch (Exception e) {
            System.err.println("Erreur lors du traitement de la candidature : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping(value = "/recherche")
    public ResponseEntity<List<Stage>> rechercheStage(
            @RequestParam(required = false) String intitule,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) ModaliteStage modalite,
            @RequestParam(required = false) String adresse) {

        return serviceEtudiant.getStagesFiltre(intitule, categorie, modalite, adresse);
    }

    @GetMapping("/getEtudiantId")
    public ResponseEntity<?> getEtudiantIdFromUserId(@RequestParam Long userId) {
        // Utilisation de DaoEtudiant pour récupérer l'étudiant via son userId
        Etudiant etudiant = daoEtudiant.findByUserId(userId);
        if (etudiant == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aucun étudiant trouvé pour l'utilisateur ID : " + userId);
        }

        // Retourner une réponse avec l'ID de l'étudiant
        Map<String, Long> response = new HashMap<>();
        response.put("etudiant_id", etudiant.getId());
        return ResponseEntity.ok(response);
    }
}

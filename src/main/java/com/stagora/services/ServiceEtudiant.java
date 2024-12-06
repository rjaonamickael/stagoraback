package com.stagora.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoStage;
import com.stagora.dao.students.DaoCandidature;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoUser;
import com.stagora.dto.DtoCandidature;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Candidature;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;
import com.stagora.utils.employeur.ModaliteStage;

@Service
public class ServiceEtudiant {

    @Autowired
    private DaoEtudiant daoEtudiant;

    @Autowired
    private DaoCandidature daoCandidature;

    @Autowired
    private DaoStage daoStage;

    @Autowired
    private DaoUser daoUser;

    // Ajouter une candidature
    public ResponseEntity<DtoCandidature> addCandidature(Long id_etudiant, DtoCandidature dtoCandidature) {
        Etudiant etudiant = daoEtudiant.findById(id_etudiant)
                .orElseThrow(() -> new NoSuchElementException("Étudiant non trouvé"));

        Candidature candidature = new Candidature();
        candidature.setDate_candidature(dtoCandidature.getDate_candidature());
        candidature.setCv(dtoCandidature.getCv());
        candidature.setLettreMotivation(dtoCandidature.getLettreMotivation());
        candidature.setEtudiant(etudiant);

        daoCandidature.save(candidature);

        return ResponseEntity.status(HttpStatus.CREATED).body(DtoCandidature.toDTOCandidature(candidature));
    }

    // Obtenir toutes les candidatures d'un étudiant avec pagination
    public ResponseEntity<Page<DtoCandidature>> getAllCandidatureByPage(Long id_etudiant, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Candidature> pageCandidature = daoCandidature.findAllByEtudiantId(id_etudiant, pageable);
        Page<DtoCandidature> pageCandidatureDTO = pageCandidature.map(DtoCandidature::toDTOCandidature);
        return ResponseEntity.ok(pageCandidatureDTO);
    }

    // Recherche de stages avec filtres
    public ResponseEntity<List<Stage>> getStagesFiltre(String intitule, String categorie, 
                                                       ModaliteStage modalite, String adresse) {
        List<Stage> stages = daoStage.findStagesByFilters(intitule, categorie, modalite, adresse);
        return ResponseEntity.status(HttpStatus.OK).body(stages);
    }

    // Récupérer le profil étudiant via l'id utilisateur
    public Etudiant getProfilByUserId(Long id_user) {
        User user = daoUser.findById(id_user)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));

        Etudiant etudiant = user.getEtudiant();
        if (etudiant == null) {
            throw new NoSuchElementException("Étudiant non trouvé pour cet utilisateur");
        }

        return etudiant;
    }

    // Mettre à jour le profil étudiant via l'id utilisateur
    public Etudiant updateProfilByUserId(Long id_user, Etudiant etudiantModif) {
        User user = daoUser.findById(id_user)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));

        Etudiant etudiant = user.getEtudiant();
        if (etudiant == null) {
            throw new NoSuchElementException("Étudiant non trouvé pour cet utilisateur");
        }

        etudiant.setNom(etudiantModif.getNom());
        etudiant.setPrenom(etudiantModif.getPrenom());
        etudiant.setApropos(etudiantModif.getApropos());
        etudiant.setAdresse(etudiantModif.getAdresse());
        etudiant.setCompetences(etudiantModif.getCompetences());
        etudiant.setExperiences(etudiantModif.getExperiences());
        etudiant.setFormation(etudiantModif.getFormation());
        etudiant.setNumeroTelephone(etudiantModif.getNumeroTelephone());

        daoEtudiant.save(etudiant);

        return etudiant;
    }
}

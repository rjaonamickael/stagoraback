package com.stagora.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoStage;
import com.stagora.dao.students.DaoCandidature;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoUser;
import com.stagora.dto.DtoCandidature;
import com.stagora.dto.EtudiantDTO;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Candidature;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;
import com.stagora.utils.employeur.ModaliteStage;
import com.stagora.utils.etudiant.EtatCandidature;
import com.stagora.utils.etudiant.SpecificationRechercheEtudiant;
import com.stagora.utils.etudiant.SpecificationRechercheStage;
import java.util.NoSuchElementException;

@Service
public class ServiceEtudiant {
	
	@Autowired
	private DaoEtudiant daoEtudiant;
	
	@Autowired
	private DaoStage daoStage;
	
	@Autowired
	private DaoCandidature daoCandidature;
	
	@Autowired
    private DaoUser daoUser;
	
	
	public ResponseEntity<DtoCandidature> addCandidature(DtoCandidature dtoCandidature) {
	    try {
	        System.out.println("Données reçues pour la candidature : " + dtoCandidature);

	        // Récupération de l'étudiant
	        Etudiant etudiant = daoEtudiant.findById(dtoCandidature.getId_etudiant())
	                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'ID : " + dtoCandidature.getId_etudiant()));
	        System.out.println("Étudiant trouvé : " + etudiant);

	        // Récupération du stage
	        Stage stage = daoStage.findById(dtoCandidature.getId_stage())
	                .orElseThrow(() -> new RuntimeException("Stage introuvable avec l'ID : " + dtoCandidature.getId_stage()));
	        System.out.println("Stage trouvé : " + stage);

	        // Création de la candidature
	        Candidature candidature = new Candidature();
	        candidature.setDate_candidature(dtoCandidature.getDate_candidature());
	        candidature.setCv(dtoCandidature.getCv());
	        candidature.setLettreMotivation(dtoCandidature.getLettreMotivation());
	        candidature.setEtudiant(etudiant);
	        candidature.setStage(stage);

	        // Sauvegarde de la candidature
	        daoCandidature.save(candidature);

	        return ResponseEntity.status(HttpStatus.CREATED).body(DtoCandidature.toDTOCandidature(candidature));
	    } catch (RuntimeException e) {
	        System.err.println("Erreur lors de l'ajout de la candidature : " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    } catch (Exception e) {
	        System.err.println("Erreur inattendue lors de l'ajout de la candidature : " + e.getMessage());
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}
	
	
	public ResponseEntity<List<Etudiant>> getEtudiantsFiltre(String nom, String competence, String formation, String experience) {
	    Specification<Etudiant> spec = SpecificationRechercheEtudiant.filtre(nom, competence, formation, experience);
	    
	    List<Etudiant> etudiants = daoEtudiant.findAll(spec);
	    
	    return ResponseEntity.status(HttpStatus.OK).body(etudiants);
	}



	
	public ResponseEntity<List<Stage> > getStagesFiltre(String intitule, String categorie, 
														ModaliteStage modalite, String adresse) {
		
		// Construire la spécification avec les critères fournis
        Specification<Stage> spec = SpecificationRechercheStage.filtre(intitule, categorie, modalite, adresse);
        
        
        // Rechercher les stages correspondants
		List<Stage> stages = daoStage.findAll(spec);
		
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

        daoEtudiant.save(etudiant);

        return etudiant;
    }
	
	// Service pour récupérer tous les étudiants
	public ResponseEntity<Page<EtudiantDTO>> getPaginatedEtudiants(int page, int size){
		//return daoEtudiant.findAll();
		// Instanciation d'un pageable pour la pagination
		Pageable pageable = PageRequest.of(page,size);
		
		// Récupération des étudiants sous forme de Page<Etudiant>
	    Page<Etudiant> studentPage = daoEtudiant.findAll(pageable);
	    
	    // Conversion de la Page<Etudiant> en Page<EtudiantDTO>
	    Page<EtudiantDTO> studentDTOPage = studentPage.map(EtudiantDTO::fromEntity);
	    
	    // Retourner la Page<EtudiantDTO>
		return ResponseEntity.ok(studentDTOPage);
	}
	
	
	
}

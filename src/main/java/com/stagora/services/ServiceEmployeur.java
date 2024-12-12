package com.stagora.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.employers.DaoSite;
import com.stagora.dao.employers.DaoStage;
import com.stagora.dao.students.DaoCandidature;
import com.stagora.dao.users.DaoUser;
import com.stagora.dto.DtoCandidature;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.employers.Site;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Candidature;
import com.stagora.entities.users.User;
import com.stagora.utils.FonctionsUtiles;
import com.stagora.utils.exceptions.EmailNonDisponibleException;
import com.stagora.utils.user.RequestInscriptionEmployeur;

@Service
public class ServiceEmployeur {
	
	@Autowired
	private DaoEmployeur daoEmployeur;
	
	@Autowired
	private DaoStage daoStage;
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoSite daoSite;
	
	@Autowired
	private DaoCandidature daoCandidature;
	
	@Autowired
	private FonctionsUtiles fonctions;
	
	private final String TYPE_MESSAGE="message";
	
	
	public ResponseEntity<Map<String, String>> ajoutStage(Long id_employeur,Stage stage){
		// Recherche de l'employeur
		Employeur employeur = daoEmployeur.findById(id_employeur)
											.orElseThrow( () ->  
											new NoSuchElementException("Employeur non trouvé"));

		// Liaison de l'offre de stage à l'employeur qui l'a créé
		stage.setEmployeur(employeur);
		
		// Enregistrement de l'offre de stage
		daoStage.save(stage);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(fonctions.reponse(TYPE_MESSAGE,"Success"));
	}
	
	
	public List<Stage> toutStage(Long id_employeur){
		// Recherche de l'employeur
		Employeur employeur = daoEmployeur.findById(id_employeur)
											.orElseThrow( () ->  
											new NoSuchElementException("Employeur non trouvé"));

		// Get All stage de l'employeur
		List<Stage> stages = employeur.getStages();
		
		
		return stages;
	}
	
	
	public Stage unStage(Long id){
		
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = daoStage.findById(id).orElseThrow(
											() -> new NoSuchElementException("Stage non trouvé"));
		
		return stage;
	}


	public ResponseEntity<Map<String, String>> suppressionStage(Long id){
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = daoStage.findById(id).orElseThrow(
				() -> new NoSuchElementException("Stage non trouvé"));
		
		// Suppression du stage
		daoStage.deleteById(stage.getId());
		
		return ResponseEntity.status(HttpStatus.OK).body(fonctions.reponse(TYPE_MESSAGE,"Success"));
	}

	
	public Stage misajourStage(Long id , Stage stageModif){
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = daoStage.findById(id).orElseThrow(
											() -> new NoSuchElementException("Stage non trouvé"));
		// Apport des modification
		stageModif.setId(id);
		stageModif.setEmployeur(stage.getEmployeur());
		
		// Enregistrement des modification
		daoStage.save(stageModif);
		
		return stageModif;
	}
	
	
	// Methode pour récupérer un employeur by id_user
	public Employeur getEmployeurByUserId(Long id_user) {
		
		// get employeur s'il existe sinon renvoie une exception
		Employeur employeur = daoEmployeur.findByUserId(id_user)
	            .orElseThrow(() -> new NoSuchElementException("Employeur non trouvé pour l'utilisateur spécifié"));
		
		return employeur;
	}
	
	// Methode pour mettre à jour un employeur by id_user
	public Employeur updateEmployeurByUserId(Long id_user, RequestInscriptionEmployeur req) {
	    // Vérification si l'utilisateur existe
	    User user = daoUser.findById(id_user)
	                       .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé"));
	    
	    // Vérification de la disponibilité du nouvel email, sauf si c'est le même email
	    if (!user.getEmail().equals(req.getUser().getEmail())) {
	        this.verifDisponibiliteEmail(req.getUser());
	    }
	    
	    // Mise à jour des informations de l'utilisateur
	    user.setEmail(req.getUser().getEmail());
	    user.setMot_de_passe(req.getUser().getMot_de_passe());
	    user.setPhone(req.getUser().getPhone());
	    
	    daoUser.save(user); // Enregistrement des modifications utilisateur
	    
	    // Récupération de l'employeur existant lié à cet utilisateur
	    Employeur employeur = user.getEmployeur();
	    if (employeur == null) {
	        throw new NoSuchElementException("Employeur non trouvé pour cet utilisateur");
	    }
	    
	    // Mise à jour des informations de l'employeur
	    employeur.setNom(req.getEmployeur().getNom());
	    employeur.setCode(req.getEmployeur().getCode());
	    
	    // Ajouter d'autres champs à mettre à jour ici pour l'employeur
	    // employeur.setSites(req.getEmployeur().getSites()); // Si vous voulez mettre à jour les sites

	    // Mise à jour des sites
	    List<Site> nouveauxSites = req.getEmployeur().getSites();
	    if (nouveauxSites != null) {
	        // Détacher les anciens sites
	        List<Site> anciensSites = employeur.getSites();
	        if (anciensSites != null) {
	            for (Site site : anciensSites) {
	                site.setEmployeur(null); // Supprimer la liaison avec l'employeur
	                daoSite.delete(site);   // Supprimer les anciens sites si nécessaire
	            }
	            employeur.getSites().clear(); // Vider la liste des anciens sites
	        }

	        // Ajouter les nouveaux sites et les rattacher à l'employeur
	        for (Site site : nouveauxSites) {
	            site.setId(null); // Si vous voulez recréer les sites (sinon, gérez les IDs)
	            site.setEmployeur(employeur); // Rattacher le site à l'employeur
	            employeur.getSites().add(site); // Ajouter le site à la liste
	        }
	    }
	    
	    // Sauvegarde de l'employeur mis à jour
	    daoEmployeur.save(employeur);

	    return employeur; // Renvoi de l'objet employeur mis à jour
	}
	
	
	// Méthode pour récupérer toutes les candidatures d'un employeur par page
	public ResponseEntity<Page<DtoCandidature>> getAllCandidatureByPage(Long idEmployeur,int page, int size){ 
		
		// instanciation d'un pageable pour la pagination
		Pageable pageable = PageRequest.of(page, size);
		
		// get des candidatures en utilisant un pageable
		Page<Candidature> pageCandidature = daoCandidature.findAllCandidatureEmployeur(idEmployeur, pageable);
		
		// Conversion de la pageCandidature en pageCandidatureDTO
		Page<DtoCandidature> pageCandidatureDTO = pageCandidature.map(DtoCandidature::toDTOCandidature);
		
		
		return ResponseEntity.ok(pageCandidatureDTO);
	}
	
	
	// Récupérer toutes les candidatures d'un stage par page
	public ResponseEntity<Page<DtoCandidature>> getAllCandidatureStageByPage(Long id_stage,int page, int size){ 
		
		// instanciation d'un pageable pour la pagination
		Pageable pageable = PageRequest.of(page, size);
		
		// get des candidatures en utilisant un pageable
		Page<Candidature> pageCandidature = daoCandidature.findCandidatureByStage(id_stage, pageable);
		
		// Conversion de la pageCandidature en pageCandidatureDTO
		Page<DtoCandidature> pageCandidatureDTO = pageCandidature.map(DtoCandidature::toDTOCandidature);
		
		
		return ResponseEntity.ok(pageCandidatureDTO);
	}
	
	
	
	
	
	
	
	private void verifDisponibiliteEmail(User user) {
        if (daoUser.findUserByEmail(user.getEmail()) != null) {
            throw new EmailNonDisponibleException("Email non disponible");
        }
    }

    
	// Methode reponse
	private Map<String, String> reponse(String message) {
        Map<String, String> reponse = new HashMap<>();
        reponse.put("message", message);
        return reponse;
    }
}

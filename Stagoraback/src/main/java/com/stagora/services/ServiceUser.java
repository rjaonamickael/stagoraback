package com.stagora.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.students.DaoEtablissement;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoConnexion;
import com.stagora.dao.users.DaoUser;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.Connexion;
import com.stagora.entities.users.User;
import com.stagora.utils.exceptions.EmailNonDisponibleException;
import com.stagora.utils.user.RequestInscriptionEmployeur;
import com.stagora.utils.user.RequestInscriptionEtudiant;
import com.stagora.utils.user.TypeCompte;


@Service
public class ServiceUser {

	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoEtudiant daoEtudiant;
	
	@Autowired
	private DaoEtablissement daoEtablissement;
	
	@Autowired
	private DaoEmployeur daoEmployeur;
	
	@Autowired
	private DaoConnexion daoConnexion;
	
	// ResponseEntity pour les type de réponses  à envoyer (HTTP)
 	public ResponseEntity<String> inscriptionEtudiant(RequestInscriptionEtudiant req){
		//Vérification de la disponibilité de l'émail
		User user = req.getUser();
		this.verifDisponibiliteEmail(user);
		
		// Instanciation des autres objets pour l'inscription
		Etudiant etudiant = req.getEtudiant();
		Optional<Etablissement> etablissement = daoEtablissement.findById(req.getId_etablissement());
		
		
		// Enregistrement de l'utilisateur
		user.setTypeCompte(TypeCompte.ETUDIANT);
		daoUser.save(user);
		
		// Association du compte à l'utilisateur et enregistrer le compte
		etudiant.setUser(user);
		etudiant.setEtablissement(etablissement.get());
		daoEtudiant.save(etudiant);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Success");
	}
	
	public ResponseEntity<String> inscriptionEmployeur(RequestInscriptionEmployeur req){
		//Vérification de la disponibilité de l'émail
		User user = req.getUser();
		this.verifDisponibiliteEmail(user);
		
		// Instanciation des autres objets pour l'inscription
		Employeur employeur = req.getEmployeur();
		
		// Enregistrement de l'utilisateur
		user.setTypeCompte(TypeCompte.EMPLOYEUR);
		daoUser.save(user);
		
		// Association du compte à l'utilisateur
		employeur.setUser(user);
		
		// Association des sites à l'employeur
		employeur.getSites().forEach(site -> site.setEmployeur(employeur));
		
		// Enregistrement de l'employeur
		daoEmployeur.save(employeur);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("Success");
		
	}

	public ResponseEntity<String> connection(String email,String mdp) {
		User user = daoUser.findUserByEmail(email);
		
		// Vérification de l'existence de l'utilisateur
		if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
	    }
		
		// Vérification de l'activation de l'émail
	    if (!user.isConfirme()) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email non confirmé");
	    }
	    
	    
	    // Vérification du mot de passe
	    if(this.verifMotdPasse(user, mdp)) {
	    	
	    	// Vérification de connection déjà établie par l'utilisateur
	    	Connexion connexion =  daoConnexion.findConnexionByUserId(user.getId());
	    	
	    	// Incrémentation des nombres de connexion
	    	if(connexion!=null) {
	    		connexion.setNombre_connexion(connexion.getNombre_connexion() + 1);
	    	}
	    	else {
	    		connexion = new Connexion();
	    	}
	    	
	    	// Changement de la date et l'heure de connexion	
	    	connexion.setDate_connexion(LocalDateTime.now());
	    	
	    	// Liaison des objets connexion et user
	    	connexion.setUser(user);
	    	
	    	// Enregistrement 
	    	daoConnexion.save(connexion);

	    	return ResponseEntity.status(HttpStatus.OK).body("Connection succes");
	    }
	    else {
	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mauvais mot de passe");
	    }
	    
	}

	

	
	private void verifDisponibiliteEmail(User user) {
		if(daoUser.findUserByEmail(user.getEmail())!=null) {
			throw new EmailNonDisponibleException("Email non disponible");
		}
	}
	
	private boolean verifMotdPasse(User user,String mdp) {
		
		if(user.getMot_de_passe().equals(mdp)) {
			return true;
		}
		else {
			return false;
		}
	}
}

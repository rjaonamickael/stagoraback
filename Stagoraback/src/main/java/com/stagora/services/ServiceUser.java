package com.stagora.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.students.DaoEtablissement;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoUser;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;
import com.stagora.utils.user.RequestInscriptionEmployeur;
import com.stagora.utils.user.RequestInscriptionEtudiant;

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
	
	
	public void inscriptionEtudiant(RequestInscriptionEtudiant req){
		//Instanciation des objets
		User user = req.getUser();
		Etudiant etudiant = req.getEtudiant();
		Optional<Etablissement> etablissement = daoEtablissement.findById(req.getId_etablissement());
		
		// Enregistrement de l'utilisateur
		daoUser.save(user);
		
		// Association du compte à l'utilisateur et enregistrer le compte
		etudiant.setUser(user);
		etudiant.setEtablissement(etablissement.get());
		daoEtudiant.save(etudiant);
	}
	
	public void inscriptionEmployeur(RequestInscriptionEmployeur req){
		//Instanciation des objets
		User user = req.getUser();
		Employeur employeur = req.getEmployeur();
		
		// Enregistrement de l'utilisateur
		daoUser.save(user);
		
		// Association du compte à l'utilisateur
		employeur.setUser(user);
		
		// Association des sites à l'employeur
		employeur.getSites().forEach(site -> site.setEmployeur(employeur));
		
		// Enregistrement de l'employeur
		daoEmployeur.save(employeur);
		
	}
}

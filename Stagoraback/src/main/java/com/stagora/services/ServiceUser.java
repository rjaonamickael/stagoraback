package com.stagora.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dao.students.DaoEtablissement;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoUser;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;
import com.stagora.utils.user.RequestInscriptionEtudiant;

@RestController
@CrossOrigin("*") 
public class ServiceUser {
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoEtudiant daoEtudiant;
	
	@Autowired
	private DaoEtablissement daoEtablissement;
	
	// Inscription d'un étudiant
	@RequestMapping(value="/user/inscription/etudiant",method=RequestMethod.POST)
	public boolean addEtudiant(@RequestBody RequestInscriptionEtudiant req){
		try {
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
			
			
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;
		}
	}
}

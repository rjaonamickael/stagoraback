package com.stagora.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.employers.DaoSite;
import com.stagora.dao.students.DaoEtablissement;
import com.stagora.dao.students.DaoEtudiant;
import com.stagora.dao.users.DaoUser;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.employers.Site;
import com.stagora.entities.students.Etablissement;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;
import com.stagora.services.ServiceUser;
import com.stagora.utils.user.RequestInscriptionEmployeur;
import com.stagora.utils.user.RequestInscriptionEtudiant;

@RestController
@CrossOrigin("*") 
public class ControllerUser {
	
	@Autowired
	private ServiceUser serviceUser;	
	
	
	// Inscription étudiant
	@RequestMapping(value="/user/inscription/etudiant",method=RequestMethod.POST)
	public String addEtudiant(@RequestBody RequestInscriptionEtudiant req){
		
		serviceUser.inscriptionEtudiant(req);
		
		return "Success";
	}
	
	
	//Inscription Employeur
	@RequestMapping(value="/user/inscription/employeur",method=RequestMethod.POST)
	public String addEmployeur(@RequestBody RequestInscriptionEmployeur req){
		
		serviceUser.inscriptionEmployeur(req);
		
		return "Success";
	}




}

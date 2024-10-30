package com.stagora.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.dao.students.DaoEtablissement;
import com.stagora.entities.students.Etablissement;

@RestController
@CrossOrigin("*") // Pour régler ce problème Access-Control-Allow-Origin
public class ServicesAdmin {
	
	@Autowired
	private DaoEtablissement daoEtablissement;
	
	
	
	// REQUÊTE POUR TOUT CE QUI CONCERNE LES ETABLISSEMENT
	// Tous les établissement
	@RequestMapping(value="/admin/etablissements",method=RequestMethod.GET)
	public List<Etablissement> getAllEtablissement(){
		return daoEtablissement.findAll();
	}
	
	// Un établissement
	@RequestMapping(value="/admin/etablissements/{id}",method=RequestMethod.GET)
	public Optional<Etablissement> getContact(@PathVariable Long id){
		return daoEtablissement.findById(id);
		
		//Version de spring antérieur, findById.
		// Optional<Contact> , car il se pourrait que id n'existe pas
	}
	
	// Ajout d'un Etablissement
	@RequestMapping(value="/admin/etablissements",method=RequestMethod.POST)
	public Etablissement addEtablissement(@RequestBody Etablissement e){
		return daoEtablissement.save(e);
	}
	
	// Suppression d'établissement
	@RequestMapping(value="/admin/etablissements/{id}",method=RequestMethod.DELETE)
	public boolean deleteEtablissement(@PathVariable Long id){
		daoEtablissement.deleteById(id);
		return true; 			// valeur envoyé comme réponse à la requête

	}
	
	// Modification d'un établissement
	@RequestMapping(value="/admin/etablissements/{id}",method=RequestMethod.PUT)
	public Etablissement updateEtablissement(@PathVariable Long id,@RequestBody Etablissement e){
		e.setId(id);
		
		return daoEtablissement.save(e);
	}
}

package com.stagora.services;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.students.DaoEtablissement;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Etablissement;
import com.stagora.utils.FonctionsUtiles;

@Service
public class ServiceAdmin {
	
	@Autowired
	private DaoEtablissement daoEtablissement;
	
	@Autowired
	private FonctionsUtiles functions;
	
	
	private final String TYPE_MESSAGE="message";
	
	
	
 	public List<Etablissement> toutEtablissement(){
		return daoEtablissement.findAll();
	}
	
	
	public Etablissement unEtablissement(Long id){
		Etablissement etablissement =  daoEtablissement.findById(id)
												.orElseThrow(
												() -> new NoSuchElementException("Etablissement non trouvé") );
		
		return etablissement;
	}
	
	
	public ResponseEntity<Map<String, String>> ajoutEtablissement(Etablissement etablissement){
		
		// Enregistrement de l'etablissement
		daoEtablissement.save(etablissement);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(functions.reponse(TYPE_MESSAGE,"Success"));
	}
	
	
	public ResponseEntity<Map<String, String>> suppressionEtablissement(Long id){
		
		// Enregistrement de l'etablissement
		daoEtablissement.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(TYPE_MESSAGE,"Success"));
	}

	
	public ResponseEntity<Map<String, String>> misajourEtablissement(Long id , Etablissement etablissementModif){

		// Apport des modification
		etablissementModif.setId(id);
		
		// Enregistrement des modification
		daoEtablissement.save(etablissementModif);
		
		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(TYPE_MESSAGE,"Success"));
	}



}

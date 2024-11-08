package com.stagora.services;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stagora.dao.employers.DaoEmployeur;
import com.stagora.dao.employers.DaoStage;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.employers.Stage;
import com.stagora.utils.FonctionsUtiles;

@Service
public class ServiceEmployeur {
	
	@Autowired
	private DaoEmployeur daoEmployeur;
	
	@Autowired
	private DaoStage daoStage;
	
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
	
}

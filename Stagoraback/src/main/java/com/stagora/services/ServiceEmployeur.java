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
import com.stagora.utils.Functions;

@Service
public class ServiceEmployeur {
	
	@Autowired
	private DaoEmployeur daoEmployeur;
	
	@Autowired
	private DaoStage daoStage;
	
	@Autowired
	private Functions functions;
	
	private final String typeResponse="message";
	
	
	public ResponseEntity<Map<String, String>> ajoutStage(Long id_employeur,Stage stage){
		// Recherche de l'employeur
		Employeur employeur = daoEmployeur.findById(id_employeur)
											.orElseThrow( () ->  
											new NoSuchElementException("Employeur non trouvé"));

		// Liaison de l'offre de stage à l'employeur qui l'a créé
		stage.setEmployeur(employeur);
		
		// Enregistrement de l'offre de stage
		daoStage.save(stage);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(functions.reponse(typeResponse,"Success"));
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
	
	
	public Stage unStage(Long id_employeur, Long id){
		/*
		// Recherche de l'employeur
				Employeur employeur = daoEmployeur.findById(id_employeur)
													.orElseThrow( () ->  
													new NoSuchElementException("Employeur non trouvé"));

		// Get All stage de l'employeur
		List<Stage> stages = employeur.getStages();	
		
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = stages.stream()
                .filter(s -> s.getId() == id)
                .findFirst()  // Renvoie un Optional<Stage>
                .orElseThrow(() -> new NoSuchElementException("Stage non trouvé"));
		*/
		Stage stage = daoStage.findById(id).orElseThrow(
											() -> new NoSuchElementException("Stage non trouvé"));
		
		return stage;
	}


	public ResponseEntity<Map<String, String>> suppressionStage(Long id_employeur, Long id){
		// Recherche de l'employeur
		Employeur employeur = daoEmployeur.findById(id_employeur)
											.orElseThrow( () ->  
											new NoSuchElementException("Employeur non trouvé"));

		// Get All stage de l'employeur
		List<Stage> stages = employeur.getStages();	
		
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = stages.stream()
		        .filter(s -> s.getId() == id)
		        .findFirst()  // Renvoie un Optional<Stage>
		        .orElseThrow(() -> new NoSuchElementException("Stage non trouvé"));
		
		System.out.println("Stage avant suppression: " + stage.getId());
		daoStage.delete(stage);
		System.out.println("Stage après suppression");
		
		return ResponseEntity.status(HttpStatus.OK).body(functions.reponse(typeResponse,"Success"));
	}

	
	public Stage misajourStage(Long id , Stage stageModif){
		// get Stage s'il existe sinon renvoie une exception
		Stage stage = daoStage.findById(id).orElseThrow(
				() -> new NoSuchElementException("Stage non trouvé"));
		
		stageModif.setId(id);
		stageModif.setEmployeur(stage.getEmployeur());
		
		// Enregistrement des modification
		daoStage.save(stageModif);
		
		return stageModif;
	}
	
}

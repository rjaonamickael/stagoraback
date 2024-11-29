package com.stagora.restcontrollers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stagora.entities.employers.Employeur;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.users.User;
import com.stagora.services.ServiceEmployeur;
import com.stagora.utils.user.RequestInscriptionEmployeur;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin("*") 
@RequestMapping("/employeur/{id_employeur}")
public class ControllerEmployeur {
	
	@Autowired
	private ServiceEmployeur serviceEmployeur;
	
	
	// AJOUT D'UN STAGE
	@PostMapping(value="/stages")
	@Transactional
	public ResponseEntity<Map<String, String>> addStage(	@PathVariable Long id_employeur,
															@RequestBody Stage stage) {
		
		return serviceEmployeur.ajoutStage(id_employeur,stage);
	}
	
	@GetMapping(value="/stages")
	//@Transactional   Pas vraiment nécessaire
	public List<Stage> getAllStage(	@PathVariable Long id_employeur) {
		
		return serviceEmployeur.toutStage(id_employeur);
	}
	
	
	@GetMapping(value="/stages/{id}")
	//@Transactional   Pas vraiment nécessaire
	public Stage getStage(@PathVariable Long id) {
		
		return serviceEmployeur.unStage(id);
	}
	
	
	@DeleteMapping(value="/stages/{id}")
	//@Transactional
	public ResponseEntity<Map<String, String>> deleteStage(@PathVariable Long id) {
		
		return serviceEmployeur.suppressionStage(id);
	}
	
	
	@PutMapping(value="/stages/{id}")
	@Transactional
	public Stage updateStage(@PathVariable Long id, @RequestBody Stage stage) {
		
		return serviceEmployeur.misajourStage(id,stage);
	}
	
	// Méthode pour récupérer un employeur par son userId
    @GetMapping(value = "/user/{id_user}")
    public ResponseEntity<Employeur> getEmployeurByUserId(@PathVariable Long id_user) {
        try {
            Employeur employeur = serviceEmployeur.getEmployeurByUserId(id_user);
            return ResponseEntity.ok(employeur);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); // Optionnel : vous pouvez aussi envoyer un message d'erreur ici.
        }
    }
    
    // Mettre a jour un employeur en utilisant id_user 
    @PutMapping("/user/{id_user}")
    @Transactional
    public ResponseEntity<String> updateEmployeurByUserId(@PathVariable Long id_user, @RequestBody RequestInscriptionEmployeur req) {
        try {
            // Appel au service pour mettre à jour l'employeur en utilisant l'id_user et l'objet RequestInscriptionEmployeur
            serviceEmployeur.updateEmployeurByUserId(id_user, req);

            // Réponse de succès si tout se passe bien
            return ResponseEntity.ok("Employeur mis à jour avec succès.");
        } catch (NoSuchElementException e) {
            // En cas d'erreur (si l'employeur ou l'utilisateur n'est pas trouvé)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employeur non trouvé.");
        }
    }


    


}

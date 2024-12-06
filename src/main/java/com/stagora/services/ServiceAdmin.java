package com.stagora.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stagora.dao.students.DaoEtablissement;
import com.stagora.entities.employers.Stage;
import com.stagora.entities.students.Etablissement;
import com.stagora.utils.FonctionsUtiles;

@Service
public class ServiceAdmin {
	
	private static final String UPLOAD_DIR = System.getProperty("user.dir") +  "/src/main/resources/images/etablissement/";
	
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
	
	
	public ResponseEntity<Etablissement> ajoutEtablissement(
												String nom, String ville, String province, 
												MultipartFile logo) throws Exception {
	    
		Etablissement etablissement = new Etablissement();
	    etablissement.setNom(nom);
	    etablissement.setVille(ville);
	    etablissement.setProvince(province);

	    if (logo != null && !logo.isEmpty()) {
		    // Extraire l'extension du fichier
		    String originalFileName = logo.getOriginalFilename();
		    String extension = "";
		    
		    // Vérifie si le fichier a une extension
		    int dotIndex = originalFileName.lastIndexOf('.');
		    if (dotIndex > 0) {
		        extension = originalFileName.substring(dotIndex);
		    }

		    // Crée un nouveau nom unique (par exemple, en utilisant le temps actuel)
		    String newFileName = "logo " + nom + extension;
		    
		    // Définir le chemin complet du fichier
		    Path filePath = Paths.get(UPLOAD_DIR, newFileName);

	        // Crée le répertoire si inexistant
	        Files.createDirectories(Paths.get(UPLOAD_DIR));

	        // Sauvegarde le fichier
	        Files.copy(logo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	        
	        System.out.println("Logo établissement enregistré à : " + filePath.toString());
	        
	        etablissement.setLogo(filePath.toString());
	        
	    } else {
	        // Optionnel : valeur par défaut si aucun fichier n'est téléchargé
	        etablissement.setLogo(null); // ou une image par défaut
	    }

		// Enregistrement de l'etablissement
		daoEtablissement.save(etablissement);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(etablissement);
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

package com.stagora.restcontrollers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.stagora.dao.students.DaoEtablissement;
import com.stagora.entities.students.Etablissement;
import com.stagora.services.ServiceAdmin;
import com.stagora.utils.FonctionsUtiles;

@RestController
@CrossOrigin("*") // To resolve CORS issues
@RequestMapping("/admin")
public class ControllerAdmin {

    @Autowired
    private DaoEtablissement daoEtablissement;
    
	@Autowired
	private FonctionsUtiles functions;
    
    @Autowired
	private ServiceAdmin serviceAdmin;

    // Retrieve all establishments
    @GetMapping("/etablissements")
    public List<Etablissement> getAllEtablissement() {
        
    	return serviceAdmin.toutEtablissement();
    }

    // Retrieve a single establishment by ID
    @GetMapping("/etablissements/{id}")
    public Etablissement getEtablissement(@PathVariable Long id) {
        
    	return serviceAdmin.unEtablissement(id);
    }

    // Add an establishment with an optional logo
    @PostMapping(value = "/etablissements", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Etablissement> addEtablissement(
            @RequestParam String nom,
            @RequestParam String ville,
            @RequestParam String province,
            @RequestParam(required = false) MultipartFile logo) {
        
        try {
            // Loguer les données reçues
            System.out.println("Nom : " + nom);
            System.out.println("Ville : " + ville);
            System.out.println("Province : " + province);
            if (logo != null) {
                System.out.println("Nom du fichier : " + logo.getOriginalFilename());
                System.out.println("Type du fichier : " + logo.getContentType());
                System.out.println("Taille du fichier (en octets) : " + logo.getSize());
            } else {
                System.out.println("Aucun fichier reçu pour le logo.");
            }

            return serviceAdmin.ajoutEtablissement(nom, ville, province, logo);
            
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur complète
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Delete an establishment by ID
    @DeleteMapping("/etablissements/{id}")
    @Transactional
    public boolean deleteEtablissement(@PathVariable Long id) {
        
    	serviceAdmin.suppressionEtablissement(id);
        
        return true; // Returns true to indicate successful deletion
    }
    
    
    // Update an existing establishment with optional logo update
    @PutMapping(value = "/etablissements/{id}", consumes = {"multipart/form-data"})
    @Transactional
    public Etablissement updateEtablissement(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("ville") String ville,
            @RequestParam("province") String province,
            @RequestParam(value = "logo", required = false) MultipartFile logo) {

        Optional<Etablissement> etablissementOpt = daoEtablissement.findById(id);
        if (!etablissementOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Etablissement not found");
        }

        Etablissement etablissement = etablissementOpt.get();
        etablissement.setNom(nom);
        etablissement.setVille(ville);
        etablissement.setProvince(province);

        if (logo != null && !logo.isEmpty()) {
            try {
                String savedLogoFilename = saveLogo(logo);
                etablissement.setLogo("/images/etablissement/" + savedLogoFilename);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Logo upload failed");
            }
        }

        return daoEtablissement.save(etablissement);
    }



 // Méthode privée pour sauvegarder le fichier de logo
    private String saveLogo(MultipartFile logo) throws IOException {
        String directoryPath = "D:/session5/projet/stagorafront/src/files/images/etablissement";
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            System.out.println("Répertoire créé : " + created);
        }

        String filename = System.currentTimeMillis() + "_" + logo.getOriginalFilename();
        File fileToSave = new File(directory, filename);

        // Loguer les informations avant la sauvegarde
        System.out.println("Chemin du fichier à sauvegarder : " + fileToSave.getAbsolutePath());
        
        logo.transferTo(fileToSave); // Sauvegarde le fichier
        
        System.out.println("Fichier sauvegardé avec succès !");
        return filename;
    }





    // Validate file type and size
    private void validateLogoFile(MultipartFile logo) {
        String contentType = logo.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/gif"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported file type. Only JPEG, PNG, and GIF are allowed.");
        }
        if (logo.getSize() > 5 * 1024 * 1024) { // 5 MB limit
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds the 5MB limit.");
        }
    }
}

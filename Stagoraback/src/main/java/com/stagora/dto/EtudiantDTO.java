package com.stagora.dto;

import java.util.List;

import com.stagora.entities.students.Competence;
import com.stagora.entities.students.Etudiant;
import com.stagora.entities.students.Experience;
import com.stagora.entities.students.Formation;

public class EtudiantDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String apropos;
    private String adresse;
    private List<Competence> competences; // Utilisation directe des entités
    private List<Experience> experiences; // Utilisation directe des entités
    private List<Formation> formations; // Utilisation directe des entités
    private String email;
    private String phone;
    private String typeCompte;
    private String etablissement; // Nom de l'établissement

    // Constructeur
    public EtudiantDTO(
        Long id,
        String nom,
        String prenom,
        String apropos,
        String adresse,
        List<Competence> competences,
        List<Experience> experiences,
        List<Formation> formations,
        String email,
        String phone,
        String etablissement
    ) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.apropos = apropos;
        this.adresse = adresse;
        this.competences = competences;
        this.experiences = experiences;
        this.formations = formations;
        this.email = email;
        this.phone = phone;
        this.etablissement = etablissement;
    }

    // Méthode de conversion depuis l'entité Etudiant
    public static EtudiantDTO fromEntity(Etudiant etudiant) {
        return new EtudiantDTO(
            etudiant.getId(),
            etudiant.getNom(),
            etudiant.getPrenom(),
            etudiant.getApropos(),
            etudiant.getAdresse(),
            etudiant.getCompetences(),
            etudiant.getExperiences(),
            etudiant.getFormation(),
            etudiant.getUser() != null ? etudiant.getUser().getEmail() : null,
            etudiant.getUser() != null ? etudiant.getUser().getPhone() : null,
            etudiant.getEtablissement() != null ? etudiant.getEtablissement().getNom() : null
        );
    }

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getApropos() { return apropos; }
    public void setApropos(String apropos) { this.apropos = apropos; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public List<Competence> getCompetences() { return competences; }
    public void setCompetences(List<Competence> competences) { this.competences = competences; }

    public List<Experience> getExperiences() { return experiences; }
    public void setExperiences(List<Experience> experiences) { this.experiences = experiences; }

    public List<Formation> getFormations() { return formations; }
    public void setFormations(List<Formation> formations) { this.formations = formations; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getTypeCompte() { return typeCompte; }
    public void setTypeCompte(String typeCompte) { this.typeCompte = typeCompte; }

    public String getEtablissement() { return etablissement; }
    public void setEtablissement(String etablissement) { this.etablissement = etablissement; }
}




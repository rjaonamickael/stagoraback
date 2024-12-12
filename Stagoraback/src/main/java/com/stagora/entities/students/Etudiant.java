package com.stagora.entities.students;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stagora.entities.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Etudiant {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	@Column(columnDefinition = "TEXT")
	private String apropos;
	
	private String adresse;
	
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonManagedReference	// Pour éviter les boucles infini JSON à cause des relations oneToMany
    private List<Competence> competences;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonManagedReference	// Pour éviter les boucles infini JSON à cause des relations oneToMany
    private List<Experience> experiences;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonManagedReference	// Pour éviter les boucles infini JSON à cause des relations oneToMany
    private List<Formation> formation;

	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
	@JsonManagedReference("etudiant-user")
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "id_etablissement", referencedColumnName = "id")
	@JsonBackReference("etudiant-etablissement")
    private Etablissement etablissement;
	
	
	@OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
	@JsonManagedReference("etudiant-canditature")
	private List<Candidature> candidatures;
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public String getApropos() {
		return apropos;
	}



	public void setApropos(String apropos) {
		this.apropos = apropos;
	}
	
	public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Formation> getFormation() {
        return formation;
    }

    public void setFormation(List<Formation> formation) {
        this.formation = formation;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public Etablissement getEtablissement() {
		return etablissement;
	}



	public void setEtablissement(Etablissement etablissement) {
		this.etablissement = etablissement;
	}

	
	

	public List<Candidature> getCandidatures() {
		return candidatures;
	}



	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}



	public Etudiant(Long id, String nom, String prenom, String apropos, User user, Etablissement etablissement, List<Candidature> candidatures) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.apropos = apropos;
		this.user = user;
		this.etablissement = etablissement;
		this.candidatures = candidatures;
	}



	public Etudiant() {
		super();
	}
	
	
	
}

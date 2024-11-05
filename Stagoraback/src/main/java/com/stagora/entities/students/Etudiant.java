package com.stagora.entities.students;

import com.stagora.entities.users.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Etudiant {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;
	
	@OneToOne
    @JoinColumn(name = "id_etablissement", referencedColumnName = "id")
    private Etablissement etablissement;


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

	
	

	public Etudiant(Long id, String nom, String prenom, User user, Etablissement etablissement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.user = user;
		this.etablissement = etablissement;
	}


	public Etudiant() {
		super();
	}
	
	
	//private Long id_etablissement;
	
	
}

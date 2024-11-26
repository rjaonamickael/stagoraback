package com.stagora.entities.students;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stagora.entities.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Etudiant {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String prenom;
	
	@Column(columnDefinition = "TEXT")
	private String apropos;
	
	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
	@JsonManagedReference("etudiant-user")
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "id_etablissement", referencedColumnName = "id")
	@JsonManagedReference("etudiant-etablissement")
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



	public String getApropos() {
		return apropos;
	}



	public void setApropos(String apropos) {
		this.apropos = apropos;
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

	

	public Etudiant(Long id, String nom, String prenom, String apropos, User user, Etablissement etablissement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.apropos = apropos;
		this.user = user;
		this.etablissement = etablissement;
	}



	public Etudiant() {
		super();
	}
	
	
	
}

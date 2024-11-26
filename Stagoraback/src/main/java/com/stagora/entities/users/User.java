package com.stagora.entities.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stagora.entities.employers.Employeur;
import com.stagora.entities.students.Etudiant;
import com.stagora.utils.user.TypeCompte;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String phone;
	
	private String sel;
	
	private String mot_de_passe;
	
	private boolean confirme=false;
	
	@Enumerated(EnumType.STRING)
    private TypeCompte typeCompte;
	
	
    // Relation OneToOne avec Etudiant et Employeur
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference("etudiant-user")
    private Etudiant etudiant;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Employeur employeur;
    
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE )  // Juste pour la mise à jour avec MERGE
    @JsonBackReference("connexion-user")
    private Connexion connexion;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getSel() {
		return sel;
	}


	public void setSel(String sel) {
		this.sel = sel;
	}


	public String getMot_de_passe() {
		return mot_de_passe;
	}


	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}


	public boolean isConfirme() {
		return confirme;
	}


	public void setConfirme(boolean confirme) {
		this.confirme = confirme;
	}


	
	public TypeCompte getTypeCompte() {
		return typeCompte;
	}


	public void setTypeCompte(TypeCompte typeCompte) {
		this.typeCompte = typeCompte;
	}


	public Etudiant getEtudiant() {
		return etudiant;
	}


	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}


	public Employeur getEmployeur() {
		return employeur;
	}


	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}


	public Connexion getConnexion() {
		return connexion;
	}


	public void setConnexion(Connexion connexion) {
		this.connexion = connexion;
	}


	public User(Long id, String email, String phone, String sel, String mot_de_passe, boolean confirme,
			TypeCompte typeCompte, Etudiant etudiant, Employeur employeur, Connexion connexion) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.sel = sel;
		this.mot_de_passe = mot_de_passe;
		this.confirme = confirme;
		this.typeCompte = typeCompte;
		this.etudiant = etudiant;
		this.employeur = employeur;
		this.connexion = connexion;
	}


	public User() {
		super();
	}

	
}

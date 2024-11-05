package com.stagora.entities.users;

import com.stagora.entities.employers.Employer;
import com.stagora.entities.students.Etudiant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
	
	
    // Relation OneToOne avec Etudiant et Employeur
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Etudiant etudiant;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Employer employeur;

	
    
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

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

//	public Employer getEmployeur() {
//		return employeur;
//	}
//
//	public void setEmployeur(Employer employeur) {
//		this.employeur = employeur;
//	}

	public User(Long id, String email, String phone, String sel, String mot_de_passe, Etudiant etudiant,
			Employer employeur) {
		super();
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.sel = sel;
		this.mot_de_passe = mot_de_passe;
		this.etudiant = etudiant;
//		this.employeur = employeur;
	}

	public User() {
		super();
	}
    
    
    
    
	
}

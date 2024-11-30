package com.stagora.entities.students;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Etablissement implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String ville;
	
	private String province;
	
	private String logo;
	
	
	// Relation OneToMany avec Etudiant
	@OneToMany(mappedBy = "etablissement", cascade = CascadeType.ALL)
	@JsonManagedReference("etudiant-etablissement")
    private List<Etudiant> etudiants;


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


	public String getVille() {
		return ville;
	}


	public void setVille(String ville) {
		this.ville = ville;
	}


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public List<Etudiant> getEtudiants() {
		return etudiants;
	}


	public void setEtudiants(List<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}


	public Etablissement(Long id, String nom, String ville, String province, String logo, List<Etudiant> etudiants) {
		super();
		this.id = id;
		this.nom = nom;
		this.ville = ville;
		this.province = province;
		this.logo = logo;
		this.etudiants = etudiants;
	}


	public Etablissement() {
		super();
	}




	
	
	
	

}

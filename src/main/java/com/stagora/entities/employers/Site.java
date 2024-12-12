package com.stagora.entities.employers;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Site implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String ville;
	
	private String province;
	
	
	
	@ManyToOne
    @JoinColumn(name = "id_employeur", referencedColumnName = "id")
	@JsonBackReference			// Pour éviter les boucles infini JSON à cause des relations ManyToOne
    private Employeur employeur;


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


	public Employeur getEmployeur() {
		return employeur;
	}


	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}


	public Site(Long id, String nom, String ville, String province, Employeur employeur) {
		super();
		this.id = id;
		this.nom = nom;
		this.ville = ville;
		this.province = province;
		this.employeur = employeur;
	}


	public Site() {
		super();
	}
	
	
}

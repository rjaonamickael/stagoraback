package com.stagora.entities.employers;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stagora.entities.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;



@Entity
public class Employeur implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nom;
	
	private String code;
	
	@OneToMany(mappedBy = "employeur", cascade = CascadeType.ALL)
	@JsonManagedReference	// Pour éviter les boucles infini JSON à cause des relations oneToMany
	private List<Site> sites;
	
	@OneToMany(mappedBy = "employeur", cascade = CascadeType.ALL)
	@JsonManagedReference	// Pour éviter les boucles infini JSON à cause des relations oneToMany
	private List<Stage> stages;
	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	
	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}


	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	
	
	public Employeur(Long id, String nom, String code, List<Site> sites, List<Stage> stages, User user) {
		super();
		this.id = id;
		this.nom = nom;
		this.code = code;
		this.sites = sites;
		this.stages = stages;
		this.user = user;
	}

	public Employeur() {
		super();
	}
	
	
	
	
	
}

package com.stagora.entities.employers;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Stage implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String intitule;
	
	@Column(columnDefinition = "TEXT") // Puisque la description peut être une longue texte
	private String description;
	
	private String categorie;
	
	private boolean ouvert=true;
	
	private int nombre_poste;
	
	private String adresse;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "DATE")
	private Date date_debut;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "DATE")
	private Date date_fin;
	
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

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public int getNombre_poste() {
		return nombre_poste;
	}

	public void setNombre_poste(int nombre_poste) {
		this.nombre_poste = nombre_poste;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public Employeur getEmployeur() {
		return employeur;
	}

	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}

	public Stage(Long id, String intitule, String description, String categorie, boolean ouvert, int nombre_poste,
			String adresse, Date date_debut, Date date_fin, Employeur employeur) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.description = description;
		this.categorie = categorie;
		this.ouvert = ouvert;
		this.nombre_poste = nombre_poste;
		this.adresse = adresse;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.employeur = employeur;
	}

	public Stage() {
		super();
	}
	
	
	
	
}

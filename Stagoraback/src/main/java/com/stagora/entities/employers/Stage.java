package com.stagora.entities.employers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stagora.entities.students.Candidature;

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
public class Stage implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String intitule;
	
	private String categorie;
	
	private int nombre_poste;
	
	
	@Column(columnDefinition = "TEXT") // Puisque la description peut être une longue texte
	private String description;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "DATE")
	private Date date_debut;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "DATE")
	private Date date_fin;
	
	
	private String TypeStage;
	
	private String modalite;
	
	
	@Column(columnDefinition = "TEXT")
	private String competences;
	
	private String niveau;
	
	private int remuneration;
	
	private String adresse;
	
	
	@Column(columnDefinition = "TEXT")
	private String autres;
	
	private boolean ouvert=true;
	
	@ManyToOne
    @JoinColumn(name = "id_employeur", referencedColumnName = "id")
	@JsonBackReference			// Pour éviter les boucles infini JSON à cause des relations ManyToOne
	private Employeur employeur;
	
	@OneToMany(mappedBy = "stage", cascade = CascadeType.ALL)
    @JsonManagedReference("stage-canditature")
	private List<Candidature> candidatures;
	
	
	
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

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getNombre_poste() {
		return nombre_poste;
	}

	public void setNombre_poste(int nombre_poste) {
		this.nombre_poste = nombre_poste;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getTypeStage() {
		return TypeStage;
	}

	public void setTypeStage(String typeStage) {
		TypeStage = typeStage;
	}

	public String getModalite() {
		return modalite;
	}

	public void setModalite(String modalite) {
		this.modalite = modalite;
	}

	public String getCompetences() {
		return competences;
	}

	public void setCompetences(String competences) {
		this.competences = competences;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public int getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(int remuneration) {
		this.remuneration = remuneration;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getAutres() {
		return autres;
	}

	public void setAutres(String autres) {
		this.autres = autres;
	}

	public boolean isOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

	public Employeur getEmployeur() {
		return employeur;
	}

	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	
	public List<Candidature> getCandidatures() {
		return candidatures;
	}
	
	public void setCandidatures(List<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

	
	
	
	public Stage(Long id, String intitule, String categorie, int nombre_poste, String description, Date date_debut,
			Date date_fin, String typeStage, String modalite, String competences, String niveau, int remuneration,
			String adresse, String autres, boolean ouvert, Employeur employeur, List<Candidature> candidatures) {
		super();
		this.id = id;
		this.intitule = intitule;
		this.categorie = categorie;
		this.nombre_poste = nombre_poste;
		this.description = description;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		TypeStage = typeStage;
		this.modalite = modalite;
		this.competences = competences;
		this.niveau = niveau;
		this.remuneration = remuneration;
		this.adresse = adresse;
		this.autres = autres;
		this.ouvert = ouvert;
		this.employeur = employeur;
		this.candidatures = candidatures;
	}

	public Stage() {
		super();
	}
	
	
	
	
}

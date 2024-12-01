package com.stagora.entities.students;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.stagora.entities.employers.Stage;
import com.stagora.utils.etudiant.EtatCandidature;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Candidature implements Serializable{
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(columnDefinition = "DATE")
	private Date date_candidature;
	
	@Enumerated(EnumType.STRING)
	private EtatCandidature etatCandidature = EtatCandidature.EN_ATTENTE;
	
	private String cv;
	
	@Column(columnDefinition = "TEXT")
	private String lettreMotivation;
	
	@ManyToOne
	@JoinColumn(name = "id_etudiant", referencedColumnName = "id")
	@JsonBackReference("etudiant-canditature")
	private Etudiant etudiant;
	
	
	@ManyToOne
	@JoinColumn(name = "id_stage", referencedColumnName = "id")
	@JsonBackReference("stage-canditature")
	private Stage stage;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getDate_candidature() {
		return date_candidature;
	}


	public void setDate_candidature(Date date_candidature) {
		this.date_candidature = date_candidature;
	}


	public EtatCandidature getEtatCandidature() {
		return etatCandidature;
	}


	public void setEtatCandidature(EtatCandidature etatCandidature) {
		this.etatCandidature = etatCandidature;
	}


	public String getCv() {
		return cv;
	}


	public void setCv(String cv) {
		this.cv = cv;
	}


	public String getLettreMotivation() {
		return lettreMotivation;
	}


	public void setLettreMotivation(String lettreMotivation) {
		this.lettreMotivation = lettreMotivation;
	}


	public Etudiant getEtudiant() {
		return etudiant;
	}


	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}


	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		this.stage = stage;
	}


	public Candidature(Long id, Date date_candidature, EtatCandidature etatCandidature, String cv,
			String lettreMotivation, Etudiant etudiant, Stage stage) {
		super();
		this.id = id;
		this.date_candidature = date_candidature;
		this.etatCandidature = etatCandidature;
		this.cv = cv;
		this.lettreMotivation = lettreMotivation;
		this.etudiant = etudiant;
		this.stage = stage;
	}


	public Candidature() {
		super();
	}
	
	

}

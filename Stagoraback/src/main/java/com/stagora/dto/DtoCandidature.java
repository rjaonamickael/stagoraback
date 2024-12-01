package com.stagora.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.stagora.entities.students.Candidature;

public class DtoCandidature {
	
	@JsonInclude(JsonInclude.Include.NON_NULL)   	// Pour eviter les null (optionnel)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date date_candidature;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String cv;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String lettreMotivation;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id_etudiant;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id_stage;

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

	public Long getId_etudiant() {
		return id_etudiant;
	}

	public void setId_etudiant(Long id_etudiant) {
		this.id_etudiant = id_etudiant;
	}

	public Long getId_stage() {
		return id_stage;
	}

	public void setId_stage(Long id_stage) {
		this.id_stage = id_stage;
	}
	
	public static DtoCandidature toDTOCandidature(Candidature c) {
		DtoCandidature dto = new DtoCandidature();
		dto.setId(c.getId());
		dto.setDate_candidature(c.getDate_candidature());
		dto.setCv(c.getCv());
		dto.setLettreMotivation(c.getLettreMotivation());
		dto.setId_etudiant(c.getEtudiant().getId());
		dto.setId_stage(c.getStage().getId());
		return dto;
	}
	
}

package com.stagora.entities.students;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
public class Etablissement implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nomEtablissement;
	
	private String ville;
	
	private String province;
	
	private String lienLogo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomEtablissement() {
		return nomEtablissement;
	}

	public void setNomEtablissement(String nomEtablissement) {
		this.nomEtablissement = nomEtablissement;
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

	public String getLienLogo() {
		return lienLogo;
	}

	public void setLienLogo(String lienLogo) {
		this.lienLogo = lienLogo;
	}

	public Etablissement(Long id, String nomEtablissement, String ville, String province, String lienLogo) {
		super();
		this.id = id;
		this.nomEtablissement = nomEtablissement;
		this.ville = ville;
		this.province = province;
		this.lienLogo = lienLogo;
	}

	// constructor
	public Etablissement() {
		super();
	}
	
	
	
	

}

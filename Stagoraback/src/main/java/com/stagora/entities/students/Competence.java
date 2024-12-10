package com.stagora.entities.students;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.stagora.entities.employers.Employeur;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Competence implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;
    
    
    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "id")
	@JsonBackReference			// Pour éviter les boucles infini JSON à cause des relations ManyToOne
    private Etudiant etudiant;

    public Competence() {}

    public Competence(Long id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

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
}

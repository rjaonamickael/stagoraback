package com.stagora.entities.students;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Experience implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intitule_poste")
    private String intitulePoste;

    @Column(name = "nom_employeur")
    private String nomEmployeur;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "date_debut", columnDefinition = "DATE")
    private Date dateDebut;

    @Column(name = "date_fin", columnDefinition = "DATE")
    private Date dateFin;

    public Experience() {}

    public Experience(Long id, String intitulePoste, String nomEmployeur, String details, Date dateDebut, Date dateFin) {
        this.id = id;
        this.intitulePoste = intitulePoste;
        this.nomEmployeur = nomEmployeur;
        this.details = details;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitulePoste() {
        return intitulePoste;
    }

    public void setIntitulePoste(String intitulePoste) {
        this.intitulePoste = intitulePoste;
    }

    public String getNomEmployeur() {
        return nomEmployeur;
    }

    public void setNomEmployeur(String nomEmployeur) {
        this.nomEmployeur = nomEmployeur;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}

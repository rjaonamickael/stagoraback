package com.stagora.entities.students;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Formation implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String intitule;

    @Column(name = "nom_etablissement")
    private String nomEtablissement;

    private String domaine;

    @Column(name = "date_debut", columnDefinition = "DATE")
    private Date dateDebut;

    @Column(name = "date_fin", columnDefinition = "DATE")
    private Date dateFin;

    public Formation() {}

    public Formation(Long id, String intitule, String nomEtablissement, String domaine, Date dateDebut, Date dateFin) {
        this.id = id;
        this.intitule = intitule;
        this.nomEtablissement = nomEtablissement;
        this.domaine = domaine;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
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

package com.stagora.entities.users;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Connexion {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date_connexion;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date_deconnexion;
	
	private int nombre_connexion=1;
	
	@OneToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
	@JsonManagedReference("connexion-user")
    private User user;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate_connexion() {
		return date_connexion;
	}

	public void setDate_connexion(LocalDateTime date_connexion) {
		this.date_connexion = date_connexion;
	}

	public LocalDateTime getDate_deconnexion() {
		return date_deconnexion;
	}

	public void setDate_deconnexion(LocalDateTime date_deconnexion) {
		this.date_deconnexion = date_deconnexion;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public int getNombre_connexion() {
		return nombre_connexion;
	}

	public void setNombre_connexion(int nombre_connexion) {
		this.nombre_connexion = nombre_connexion;
	}

	
	public Connexion(Long id, LocalDateTime date_connexion, LocalDateTime date_deconnexion, int nombre_connexion,
			User user) {
		super();
		this.id = id;
		this.date_connexion = date_connexion;
		this.date_deconnexion = date_deconnexion;
		this.nombre_connexion = nombre_connexion;
		this.user = user;
	}

	public Connexion() {
		super();
	}


	
	
	
}

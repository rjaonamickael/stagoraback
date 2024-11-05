package com.stagora.utils.user;

import com.stagora.entities.students.Etudiant;
import com.stagora.entities.users.User;

public class RequestInscriptionEtudiant {
	private User user;
	private Etudiant etudiant;
	private Long id_etablissement;
	
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Long getId_etablissement() {
		return id_etablissement;
	}
	public void setId_etablissement(Long id_etablissement) {
		this.id_etablissement = id_etablissement;
	}
	public RequestInscriptionEtudiant(User user, Etudiant etudiant, Long id_etablissement) {
		super();
		this.user = user;
		this.etudiant = etudiant;
		this.id_etablissement = id_etablissement;
	}
	public RequestInscriptionEtudiant() {
		super();
	}
	
	
	
	
	
	

}

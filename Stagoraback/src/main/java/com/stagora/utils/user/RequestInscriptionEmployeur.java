package com.stagora.utils.user;

import java.util.ArrayList;
import java.util.List;

import com.stagora.entities.employers.Employeur;
import com.stagora.entities.employers.Site;
import com.stagora.entities.users.User;

public class RequestInscriptionEmployeur {
	private User user;
	private Employeur employeur;
//	private List<Site> sites;
	
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Employeur getEmployeur() {
		return employeur;
	}
	
	public void setEmployeur(Employeur employeur) {
		this.employeur = employeur;
	}
	
	
	
//	public List<Site> getSites() {
//		return sites;
//	}
//
//	public void setSites(List<Site> sites) {
//		this.sites = sites;
//	}

	
	
	public RequestInscriptionEmployeur(User user, Employeur employeur) {
		super();
		this.user = user;
		this.employeur = employeur;
//		this.sites = sites;
	}

	public RequestInscriptionEmployeur() {
		super();
	}
	
	
	
}

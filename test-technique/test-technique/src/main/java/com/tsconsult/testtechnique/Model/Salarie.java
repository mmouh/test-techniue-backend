package com.tsconsult.testtechnique.Model;

import java.time.LocalDate;

public class Salarie {
	
	
	private String prenom;
	private String fonction;
	private int anneeExperience;
	private String adresse;
	
	
	
	public Salarie() {
		
	}
	
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public int getAnneeExperience() {
		return anneeExperience;
	}

	public void setAnneeExperience(int anneeExperience) {
		this.anneeExperience = anneeExperience;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		return "Salarie {prenom=" + prenom + ", fonction=" + fonction + ", anneeExperience=" + anneeExperience
				+ ", adresse=" + adresse + "}";
	}

	
	
	/*
	@Override
	public String toString() {
		return "Salarie {nom=" + nom + ", prenom=" + prenom + ", fonction=" + fonction + ", dateNaissance="
				+ dateNaissance + "}";
	}
	
	*/
	

}

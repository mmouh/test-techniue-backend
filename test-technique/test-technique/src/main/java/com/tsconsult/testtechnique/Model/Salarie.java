package com.tsconsult.testtechnique.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Salarie {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "prenom")
	private String prenom;
	
	@Column(name = "fonction")
	private String fonction;
	
	@Column(name = "annee_experience")
	private int anneeExperience;
	
	@Column(name = "adresse")
	private String adresse;
	
	 @Column(name = "salaire")
	 private double salaire;
	 
	 @Column(name = "date_naissance")
	 @JsonFormat(pattern = "yyyy-MM-dd")
	 private LocalDate dateNaissance;

	public Salarie() {

	}
	
	

	public Salarie(int id, String prenom, String fonction, int anneeExperience, String adresse, double salaire,
			LocalDate dateNaissance) {
		this.id = id;
		this.prenom = prenom;
		this.fonction = fonction;
		this.anneeExperience = anneeExperience;
		this.adresse = adresse;
		this.salaire = salaire;
		this.dateNaissance = dateNaissance;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
	
	

	public double getSalaire() {
		return salaire;
	}

	public void setSalaire(double salaire) {
		this.salaire = salaire;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
        
        
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Salarie))
			return false;
                try {
                	Salarie other = (Salarie) obj;
        return 
                this.prenom.equals(other.prenom) &&
                this.fonction.equals(other.fonction) &&
                this.anneeExperience==other.anneeExperience &&
              
                this.adresse.equals(other.adresse) &&
                this.salaire==other.salaire &&
                this.dateNaissance.equals(other.dateNaissance);
                
        } catch (NullPointerException e) {
            return false;
        }
		
	}

    @Override
    public String toString() {
        return "{" + "id:" + id + ", prenom:" + prenom + ", fonction:" + fonction + ", anneeExperience:" + anneeExperience + ", adresse:" + adresse + ", salaire:" + salaire + ", dateNaissance:" + dateNaissance + "}";
    }
}

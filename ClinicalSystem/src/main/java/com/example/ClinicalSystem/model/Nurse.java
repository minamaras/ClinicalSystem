package com.example.ClinicalSystem.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Nurse extends User {	

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;

	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Recipe> unathenticatedRecipes = new ArrayList<Recipe>();
	
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ArrayList<Recipe> athenticatedRecipes = new ArrayList<Recipe>();

	public Nurse() {
		
		super();
		this.setRole(Role.NURSE);
		
	}

	public Nurse(Clinic clinic, ArrayList<Recipe> unathenticatedRecipes, ArrayList<Recipe> athenticatedRecipes) {
		super();
		this.clinic = clinic;
		this.unathenticatedRecipes = unathenticatedRecipes;
		this.athenticatedRecipes = athenticatedRecipes;
		this.setRole(Role.NURSE);
	}

	public Nurse(ArrayList<Recipe> unathenticatedRecipes, ArrayList<Recipe> athenticatedRecipes) {
		super();
		this.unathenticatedRecipes = unathenticatedRecipes;
		this.athenticatedRecipes = athenticatedRecipes;
		this.setRole(Role.NURSE);
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public ArrayList<Recipe> getUnathenticatedRecipes() {
		return unathenticatedRecipes;
	}

	public void setUnathenticatedRecipes(ArrayList<Recipe> unathenticatedRecipes) {
		this.unathenticatedRecipes = unathenticatedRecipes;
	}

	public ArrayList<Recipe> getAthenticatedRecipes() {
		return athenticatedRecipes;
	}

	public void setAthenticatedRecipes(ArrayList<Recipe> athenticatedRecipes) {
		this.athenticatedRecipes = athenticatedRecipes;
	}
}



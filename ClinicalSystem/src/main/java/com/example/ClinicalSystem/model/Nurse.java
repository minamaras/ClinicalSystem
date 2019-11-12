package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Nurse extends User {	

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Clinic clinic;

	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Recipe> unathenticatedRecipes = new ArrayList<>();
	
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Recipe> athenticatedRecipes = new ArrayList<>();

	public Nurse() {
		super();
		this.setRole(Role.NURSE);
	}

	public Nurse(Clinic clinic, List<Recipe> unathenticatedRecipes, List<Recipe> athenticatedRecipes) {
		super();
		this.clinic = clinic;
		this.unathenticatedRecipes = unathenticatedRecipes;
		this.athenticatedRecipes = athenticatedRecipes;
		this.setRole(Role.NURSE);
	}

	public Nurse(List<Recipe> unathenticatedRecipes, List<Recipe> athenticatedRecipes) {
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

	public List<Recipe> getUnathenticatedRecipes() {
		return unathenticatedRecipes;
	}

	public void setUnathenticatedRecipes(List<Recipe> unathenticatedRecipes) {
		this.unathenticatedRecipes = unathenticatedRecipes;
	}

	public List<Recipe> getAthenticatedRecipes() {
		return athenticatedRecipes;
	}

	public void setAthenticatedRecipes(List<Recipe> athenticatedRecipes) {
		this.athenticatedRecipes = athenticatedRecipes;
	}
}



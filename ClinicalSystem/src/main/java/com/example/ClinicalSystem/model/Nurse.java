package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
	private Set<Recipe> unathenticatedRecipes = new HashSet<Recipe>();
	
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recipe> athenticatedRecipes = new HashSet<Recipe>();

	public Nurse() {
		
		super();
		this.setRole(Role.NURSE);
		
	}

	public Nurse(Clinic clinic, Set<Recipe> unathenticatedRecipes, Set<Recipe> athenticatedRecipes) {
		super();
		this.clinic = clinic;
		this.unathenticatedRecipes = unathenticatedRecipes;
		this.athenticatedRecipes = athenticatedRecipes;
		this.setRole(Role.NURSE);
	}

	public Nurse(Set<Recipe> unathenticatedRecipes, Set<Recipe> athenticatedRecipes) {
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

	public Set<Recipe> getUnathenticatedRecipes() {
		return unathenticatedRecipes;
	}

	public void setUnathenticatedRecipes(Set<Recipe> unathenticatedRecipes) {
		this.unathenticatedRecipes = unathenticatedRecipes;
	}

	public Set<Recipe> getAthenticatedRecipes() {
		return athenticatedRecipes;
	}

	public void setAthenticatedRecipes(Set<Recipe> athenticatedRecipes) {
		this.athenticatedRecipes = athenticatedRecipes;
	}
}



package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Nurse extends User {

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Clinic clinic;

	@OneToMany(mappedBy = "nurse", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Recipe> recipes = new HashSet<Recipe>();

	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Holiday> holidays = new HashSet<Holiday>();


	public Nurse() {
		super();
		this.setRole(Role.NURSE);
	}

	public Nurse(Clinic clinic) {
		super();
		this.clinic = clinic;
		this.setRole(Role.NURSE);
	}


	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	public Set<Holiday> getHolidays() {
		return holidays;
	}

	public void setHolidays(Set<Holiday> holidays) {
		this.holidays = holidays;
	}
}

package com.example.ClinicalSystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Recipe {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Doctor doctor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Nurse nurse;
	
	@ManyToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Patient patient;
	
	@Column(name = "content")
	String content;

	public Recipe() {
		
	}
	
	public Recipe(String id, Doctor doctor, Nurse nurse, Patient patient, String content) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.nurse = nurse;
		this.patient = patient;
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}

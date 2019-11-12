package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Codebook {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "medication")
	private ArrayList<String> medications = new ArrayList<String>();
	
	@Column(name = "diagnosis")
	private Set<String> diagnosis = new HashSet<String>();
	
	public Codebook() {
		
	}

	public Codebook(Long id) {
		this.id = id;
	}

	public Codebook(Long id, ArrayList<String> medications, Set<String> diagnosis) {
		super();
		this.id = id;
		this.medications = medications;
		this.diagnosis = diagnosis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<String> getMedications() {
		return medications;
	}

	public void setMedications(ArrayList<String> medications) {
		this.medications = medications;
	}

	public Set<String> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Set<String> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
	

}

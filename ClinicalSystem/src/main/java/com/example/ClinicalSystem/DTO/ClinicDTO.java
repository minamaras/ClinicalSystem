package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClinicDTO {

	private Long id;
	private String name;
	private String adress;
	private String description;
	private Set<Long> doctorsId = new HashSet<Long>();
	private double rating;
	private String examprice="";
	private List<String> patients;
	
	public ClinicDTO() {
		super();
	}

	public ClinicDTO(Clinic clinic) {
		this(clinic.getId(), clinic.getName(), clinic.getAdress(), clinic.getDescription());
	}

	public ClinicDTO(Long id, String name, String adress, String description) {
		super();
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Long> getDoctorsId() {
		return doctorsId;
	}

	public void setDoctorsId(Set<Long> doctorsId) {
		this.doctorsId = doctorsId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getExamprice() {
		return examprice;
	}

	public void setExamprice(String examprice) {
		this.examprice = examprice;
	}

	public List<String> getPatients() {
		return patients;
	}

	public void setPatients(List<String> patients) {
		this.patients = patients;
	}
}

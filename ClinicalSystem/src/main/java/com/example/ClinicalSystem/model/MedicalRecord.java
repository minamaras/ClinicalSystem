package com.example.ClinicalSystem.model;

import java.util.Set;

import javax.persistence.*;

@Entity
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
	
	@Column(name = "medicalhistory", nullable = false)
	private String medicalHistory;
	
	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinTable(name = "medicalrecord_doctor", joinColumns = @JoinColumn(name = "medicalrecord_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"))
	private Set<Doctor> doctors;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	


}

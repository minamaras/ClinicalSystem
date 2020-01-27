package com.example.ClinicalSystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;

	@Column(name = "measures")
	private String measures;

	@Column(name = "blood_type")
	private String bloodtype;

	@Column(name = "allergies")
	private String allergies;

	@Column(name = "eyes")
	private String eyes;

	@Column(name = "additional")
	private String additional;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Report> reports = new HashSet<Report>();

	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinTable(name = "medicalrecord_doctor", joinColumns = @JoinColumn(name = "medicalrecord_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private Set<Doctor> doctors = new HashSet<Doctor>();

	public MedicalRecord(){
		super();
	}

	public MedicalRecord(Patient patient, String measures, String bloodtype, String allergies, String eyes, String additional){
		super();
		this.patient = patient;
		this.measures = measures;
		this.bloodtype = bloodtype;
		this.allergies = allergies;
		this.eyes = eyes;
		this.additional = additional;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}

	public String getBloodtype() {
		return bloodtype;
	}

	public void setBloodtype(String bloodtype) {
		this.bloodtype = bloodtype;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getEyes() {
		return eyes;
	}

	public void setEyes(String eyes) {
		this.eyes = eyes;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}


}

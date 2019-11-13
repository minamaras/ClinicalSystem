package com.example.ClinicalSystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Doctor extends User {


	@Column(name = "specialization", nullable = false)
	private String specialization;

	@Column(name = "rating", nullable = false)
	private String rating;

	@ManyToMany
	@JoinTable(name = "doctor_patient", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patients = new HashSet<Patient>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Clinic clinic;

	@ManyToMany(mappedBy = "doctors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalRecord> medicalRecords = new HashSet<MedicalRecord>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Calendar calendar;


	public Doctor() {
		super();
		this.setRole(Role.DOCTOR);

	}

	public Doctor(String specialization, String rating, Set<Patient> patients, Clinic clinic, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.rating = rating;
		this.patients = patients;
		this.clinic = clinic;
		this.calendar = calendar;
		this.setRole(Role.DOCTOR);

	}

	public Doctor(String specialization, Clinic clinic, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.clinic = clinic;
		this.calendar = calendar;
		this.setRole(Role.DOCTOR);
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<MedicalRecord> getMedicalRecord() {
		return medicalRecords;
	}

	public void setMedicalRecord(Set<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}

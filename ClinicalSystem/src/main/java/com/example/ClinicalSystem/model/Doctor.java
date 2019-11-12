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
	@JoinTable(name = "doctor-patient", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patients = new HashSet<Patient>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@ManyToMany(mappedBy = "doctors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<MedicalRecord> medicalRecord;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Calendar calendar;
	

	public Doctor() {
		super();
		this.setRole(Role.DOCTOR);

	}

	public Doctor(String specialization, String rating, Set<Patient> patients, Clinic clinic,
			Set<MedicalRecord> medicalRecord, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.rating = rating;
		this.patients = patients;
		this.clinic = clinic;
		this.medicalRecord = medicalRecord;
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
		return medicalRecord;
	}

	public void setMedicalRecord(Set<MedicalRecord> medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}	
}

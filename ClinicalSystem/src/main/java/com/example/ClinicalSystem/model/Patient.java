package com.example.ClinicalSystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.print.Doc;

@Entity
public class Patient extends User {


	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointment = new HashSet<Appointment>();

	@OneToOne(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord;

	@ManyToMany(mappedBy = "patients")
	private Set<Doctor> doctors = new HashSet<Doctor>();

	@OneToMany(mappedBy = "patient",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recipe> recipes = new HashSet<Recipe>();

	public Patient(Set<Appointment> appointment, MedicalRecord medicalRecord, Set<Doctor> doctors) {
		super();
		this.appointment = appointment;
		this.medicalRecord = medicalRecord;
		this.doctors = doctors;
		this.setRole(Role.PATIENT);

	}

	public Patient() {
		super();
		this.setRole(Role.PATIENT);
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}






}

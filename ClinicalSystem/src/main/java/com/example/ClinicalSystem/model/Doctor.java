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

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ClinicAdmin clinicAdmin;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@ManyToMany(mappedBy = "doctors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalRecord> medicalRecords = new HashSet<MedicalRecord>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Calendar calendar;


	public Doctor() {
		super();
		this.setRole(Role.DOCTOR);
		this.setRating("0");

	}

	public Doctor(String specialization, String rating, Set<Patient> patients, Clinic clinic, Calendar calendar, Set<MedicalRecord> medicalRecords) {
		super();
		this.specialization = specialization;
		this.rating = rating;
		this.patients = patients;
		this.clinic = clinic;
		this.calendar = calendar;
		this.medicalRecords = medicalRecords;
		this.setRole(Role.DOCTOR);

	}

	/*public Doctor(String specialization, Clinic clinic, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.clinic = clinic;
		this.calendar = calendar;
		this.setRole(Role.DOCTOR);
	}*/

	public ClinicAdmin getClinicAdmin() {
		return clinicAdmin;
	}

	public void setClinicAdmin(ClinicAdmin clinicAdmin) {
		this.clinicAdmin = clinicAdmin;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<MedicalRecord> getMedicalRecords() {
		return medicalRecords;
	}

	public void setMedicalRecords(Set<MedicalRecord> medicalRecords) {
		this.medicalRecords = medicalRecords;
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

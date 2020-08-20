package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalTime;

import java.util.Date;
import java.sql.Time;
import java.util.*;

import javax.persistence.*;

@Entity
public class Doctor extends User {

	@Column(name = "specialization", nullable = false)
	private String specialization;

	@ManyToMany
	@JoinTable(name = "doctor_patient", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patients = new HashSet<Patient>();

	@ManyToMany
	@JoinTable(name = "doctor_patient_ratings", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
	private Set<Patient> patientsThatRated = new HashSet<Patient>();

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Clinic clinic;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(name = "doctor_ratings", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rating_id", referencedColumnName = "id"))
	private Set<Rating> singleratings = new HashSet<>();

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ExamType examType;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private ClinicAdmin clinicAdmin;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<OperationRequest> operations = new HashSet<OperationRequest>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalRecord> medicalRecords = new HashSet<MedicalRecord>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Calendar calendar;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private WorkingHours workingHours;

	@Column(name = "firstlogin", nullable = false)
	private boolean firstLogin;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Report> reports = new HashSet<Report>();

	public Doctor() {
		super();
		this.setRole(Role.DOCTOR);
		this.firstLogin = true;
	}

	public Doctor(String specialization, Set<Patient> patients, Clinic clinic, Calendar calendar, Set<MedicalRecord> medicalRecords, boolean firstLogin) {
		super();
		this.specialization = specialization;
		this.patients = patients;
		this.clinic = clinic;
		this.calendar = calendar;
		this.medicalRecords = medicalRecords;
		this.setRole(Role.DOCTOR);
		this.firstLogin = firstLogin;
	}

	public Doctor(String specialization, String name, String lastname, ExamType examType, String email) {
		this.specialization = specialization;
		this.setName(name);
		this.setLastname(lastname);
		this.setEmail(email);
		this.examType = examType;
	}

	public boolean checkDoctorWorkingHours() {
		return this.getWorkingHours().getStart().compareTo(this.getWorkingHours().getEnd()) > 0 ||
				this.getWorkingHours().getStart().compareTo(this.getWorkingHours().getEnd()) == 0;
	}

	public void addToClinic(Clinic clinic) {
		this.clinic = clinic;
		if(clinic != null) {
			this.setClinic(clinic);
			clinic.getDoctors().add(this);
		}
	}

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

	public ExamType getExamType() {
		return examType;
	}
	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public Set<Report> getReports() {
		return reports;
	}

	public void setReports(Set<Report> reports) {
		this.reports = reports;
	}

	public Set<OperationRequest> getOperations() {
		return operations;
	}

	public void setOperations(Set<OperationRequest> operations) {
		this.operations = operations;
	}

	public Set<Patient> getPatientsThatRated() {
		return patientsThatRated;
	}

	public void setPatientsThatRated(Set<Patient> patientsThatRated) {
		this.patientsThatRated = patientsThatRated;
	}

	public void addPatientThatRated(Patient p){
		this.patientsThatRated.add(p);
	}

	public void addNewSingleRating(Rating r){
		this.singleratings.add(r);
	}

	public Set<Rating> getSingleratings() {
		return singleratings;
	}

	public void setSingleratings(Set<Rating> singleratings) {
		this.singleratings = singleratings;
	}

	public WorkingHours getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(WorkingHours workingHours) {
		this.workingHours = workingHours;
	}
}
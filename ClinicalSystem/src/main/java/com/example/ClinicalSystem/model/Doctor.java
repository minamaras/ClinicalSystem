package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalTime;

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


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;

	@ManyToMany
	@JoinTable(name = "doctor_ratings", joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rating_id", referencedColumnName = "id"))
	private Set<Rating> singleratings = new HashSet<>();

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ExamType examType;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ClinicAdmin clinicAdmin;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@ManyToMany(mappedBy = "doctors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicalRecord> medicalRecords = new HashSet<MedicalRecord>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Calendar calendar;

	@Column(name="starttime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Time start;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name="endtime")
	private Time end;

	@Column(name = "firstlogin", nullable = false)
	private boolean firstLogin;


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Report> reports = new HashSet<Report>();


	public Doctor() {
		super();
		this.setRole(Role.DOCTOR);
		this.firstLogin = true;

	}

	public Doctor(String specialization, Set<Patient> patients, Clinic clinic, Calendar calendar, Set<MedicalRecord> medicalRecords, Time end, Time start, boolean firstLogin) {
		super();
		this.specialization = specialization;
		this.patients = patients;
		this.clinic = clinic;
		this.calendar = calendar;
		this.medicalRecords = medicalRecords;
		this.setRole(Role.DOCTOR);
		this.start = start;
		this.end = end;
		this.firstLogin = firstLogin;

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
	public void setExamType(ExamType examType){
			this.examType = examType;

		}
	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;

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
}

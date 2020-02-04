package com.example.ClinicalSystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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


	@OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@OneToOne(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord;

	@ManyToMany(mappedBy = "patients")
	private Set<Doctor> doctors = new HashSet<Doctor>();

	@ManyToMany(mappedBy = "patientsThatRated")
	private Set<Doctor> ratedDoctors = new HashSet<Doctor>();

	@ManyToMany(mappedBy = "patientsThatRated")
	private Set<Clinic> ratedClinics = new HashSet<Clinic>();

	@ManyToMany(mappedBy = "patients")
	private Set<Clinic> clinics = new HashSet<Clinic>();

	@OneToMany(mappedBy = "patient",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recipe> recipes = new HashSet<Recipe>();

	@Column(name = "active")
	private boolean active = false;

	@Column(name="verificationCode")
	private String verificationCode;

	@Column(name = "adress")
	private String adress;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	@Column(name = "phone")
	private String phone;

	@Column(name = "socialsecuritynumber")
	private String socialSecurityNumber;




	public Patient(Set<Appointment> appointments, MedicalRecord medicalRecord, Set<Doctor> doctors) {
		super();
		this.appointments = appointments;
		this.medicalRecord = medicalRecord;
		this.doctors = doctors;
		this.setRole(Role.PATIENT);

	}


	public Patient() {
		super();
		this.setRole(Role.PATIENT);
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
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

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public Set<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}

	public Set<Doctor> getRatedDoctors() {
		return ratedDoctors;
	}

	public void setRatedDoctors(Set<Doctor> ratedDoctors) {
		this.ratedDoctors = ratedDoctors;
	}

	public Set<Clinic> getRatedClinics() {
		return ratedClinics;
	}

	public void setRatedClinics(Set<Clinic> ratedClinics) {
		this.ratedClinics = ratedClinics;
	}

	public void addRatedDoctor(Doctor d){
		this.ratedDoctors.add(d);
	}
}

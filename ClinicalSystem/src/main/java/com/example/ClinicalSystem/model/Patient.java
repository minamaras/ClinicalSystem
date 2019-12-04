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


	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointment = new HashSet<Appointment>();

	@OneToOne(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord;

	@ManyToMany(mappedBy = "patients")
	private Set<Doctor> doctors = new HashSet<Doctor>();

	@OneToMany(mappedBy = "patient",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Recipe> recipes = new HashSet<Recipe>();

	@Column(name = "active")
	private boolean active = false;

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






}

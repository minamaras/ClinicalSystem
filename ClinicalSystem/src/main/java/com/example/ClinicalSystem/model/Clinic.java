package com.example.ClinicalSystem.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Clinic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "adress", nullable = false)
	private String adress;
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "freeAppointment")
	private String freeAppointment;
	
	@Column(name = "price", nullable = false)
	private String price;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ArrayList<Nurse> nurses = new ArrayList<Nurse>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private BusinessReport report;
	
	public Clinic() {
		
	}
	
	public Clinic(String id, String name, String adress, String description, String freeAppointment, String price) {
		super();
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.description = description;
		this.freeAppointment = freeAppointment;
		this.price = price;
		this.doctors = new ArrayList<Doctor>();
		this.nurses = new ArrayList<Nurse>();
		this.patients = new ArrayList<Patient>();
	}

	public Clinic(String id, String name, String adress, String description, String freeAppointment, String price,
			ArrayList<Doctor> doctors, ArrayList<Nurse> nurses, ArrayList<Patient> patients) {
		super();
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.description = description;
		this.freeAppointment = freeAppointment;
		this.price = price;
		this.doctors = doctors;
		this.nurses = nurses;
		this.patients = patients;
	}

	public BusinessReport getReport() {
		return report;
	}

	public void setReport(BusinessReport report) {
		this.report = report;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFreeAppointment() {
		return freeAppointment;
	}

	public void setFreeAppointment(String freeAppointment) {
		this.freeAppointment = freeAppointment;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public ArrayList<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}

	public ArrayList<Nurse> getNurses() {
		return nurses;
	}

	public void setNurses(ArrayList<Nurse> nurses) {
		this.nurses = nurses;
	}

	public ArrayList<Patient> getPatients() {
		return patients;
	}

	public void setPatients(ArrayList<Patient> patients) {
		this.patients = patients;
	}
	
	

}

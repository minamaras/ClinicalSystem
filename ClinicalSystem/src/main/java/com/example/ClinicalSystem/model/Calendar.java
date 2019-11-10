package com.example.ClinicalSystem.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

public class Calendar {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Doctor doctor;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

	public Calendar() {
		
	}
	
	public Calendar(String id, Doctor doctor) {
		super();
		this.id = id;
		this.doctor = doctor;
		this.appointments = new ArrayList<Appointment>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	
}

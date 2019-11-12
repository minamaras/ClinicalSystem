package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Calendar {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Doctor doctor;
	
	@OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	public Calendar() {
		super();
	}
	
	public Calendar(String id, Doctor doctor) {
		super();
		this.id = id;
		this.doctor = doctor;

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

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
}

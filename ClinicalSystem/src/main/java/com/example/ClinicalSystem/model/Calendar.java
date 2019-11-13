package com.example.ClinicalSystem.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Calendar {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Doctor doctor;

	@OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	public Calendar() {
		super();
	}

	public Calendar(Long id, Doctor doctor) {
		super();
		this.id = id;
		this.doctor = doctor;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

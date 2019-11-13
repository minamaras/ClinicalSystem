package com.example.ClinicalSystem.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "date" , nullable = false)
	private String date;

	@Column(name = "time", nullable = false )
	private String time;

	@Column(name = "type", nullable = false)
	private String type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private OR or;

	//@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Appointment appointment;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Calendar calendar;


	@Column(name = "hasHappend", nullable = false)
	private boolean hasHappend;

	public Appointment() {
		super();
	}

	public Appointment(Long id, String date, String time, String type, Patient patient, Doctor doctor,
			 boolean hasHappend) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.type = type;
		this.patient = patient;
		this.doctor = doctor;
		this.hasHappend = hasHappend;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}



	public boolean isHasHappend() {
		return hasHappend;
	}

	public void setHasHappend(boolean hasHappend) {
		this.hasHappend = hasHappend;
	}




}

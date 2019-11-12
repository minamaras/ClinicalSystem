package com.example.ClinicalSystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "number", nullable = false)
	private int number;
	
	@Column(name = "isreserved", nullable = false)
	private boolean isReserved;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointment;
	
	public OR() {
		super();
	}

	public OR(int number, boolean isReserved, String name, Set<Appointment> appointment) {
		super();
		this.number = number;
		this.isReserved = isReserved;
		this.name = name;
		this.appointment = appointment;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}
	
	
}

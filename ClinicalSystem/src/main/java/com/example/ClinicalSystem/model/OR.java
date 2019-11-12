package com.example.ClinicalSystem.model;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = false)
	private int number;
	
	@Column(name = "isreserved", nullable = false)
	private boolean isReserved;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();
	
	public OR() {
		super();
	}

	public OR(int number, boolean isReserved, String name, Set<Appointment> appointments) {
		super();
		this.number = number;
		this.isReserved = isReserved;
		this.name = name;
		this.appointments = appointments;
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

	public Set<Appointment> getAppointnments() {
		return appointments;
	}

	public void setAppointnments( Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	
}

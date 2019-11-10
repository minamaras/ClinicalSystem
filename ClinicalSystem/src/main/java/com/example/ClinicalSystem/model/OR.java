package com.example.ClinicalSystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class OR {

	@Column(name = "number", nullable = false)
	private int number;
	
	@Column(name = "isreserved", nullable = false)
	private boolean isReserved;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Appointment appoitnment= new Appointment();
	
	public OR() {
		super();
	}

	public OR(int number, boolean isReserved, String name, Appointment appoitnment) {
		super();
		this.number = number;
		this.isReserved = isReserved;
		this.name = name;
		this.appoitnment = appoitnment;
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

	public Appointment getAppoitnment() {
		return appoitnment;
	}

	public void setAppoitnment(Appointment appoitnment) {
		this.appoitnment = appoitnment;
	}
	
	
}

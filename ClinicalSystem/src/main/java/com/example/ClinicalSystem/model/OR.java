package com.example.ClinicalSystem.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OR {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long Id;

	@Column(name = "number", nullable = false)
	private int number;
	
	@Column(name = "isReserved", nullable = false)
	private boolean isReserved;
	
	@Column(name = "name", nullable = false)
	private String name;


	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	public OR() {
		super();
	}

	public OR(Long id, int number, boolean isReserved, String name) {
		super();
		this.Id = id;
		this.number = number;
		this.isReserved = isReserved;
		this.name = name;
	}

	public Long getId() { return Id; }

	public void setId(Long id) { this.Id = id; }

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

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointment(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	
}

package com.example.ClinicalSystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="OpRoom")
public class OR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = false)
	private int number;

	@Column(name = "isavailable")
	private boolean isAvailable;

	@Column(name = "reserved")
	private String reserved;

	@Column(name = "name", nullable = false)
	private String name;



	@OneToMany(mappedBy = "or", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();



	public OR() {
		super();
	}

	public OR(Long id, int number, boolean isAvailable, String name, String reserved) {
		super();
		this.id = id;
		this.number = number;
		this.isAvailable = isAvailable;
		this.name = name;
		this.reserved = reserved;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
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

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
}

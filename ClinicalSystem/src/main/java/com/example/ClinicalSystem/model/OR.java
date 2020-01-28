package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalTime;

import java.sql.Time;
import java.util.Date;
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

	@Column(name = "reservedFrom")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Time reservedFrom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name = "reservedTill")
	private Time reservedTill;

	@OneToMany(mappedBy = "or", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@Column(name="date_reserved")
	private Date dateReserved;

	public OR() {
		super();
	}

	public OR(Long id, int number, boolean isAvailable, String name, String reserved, Date dateReserved, Time reservedFrom,Time reservedTill) {
		super();
		this.id = id;
		this.number = number;
		this.isAvailable = isAvailable;
		this.name = name;
		this.dateReserved = dateReserved;
		this.reserved = reserved;
		this.reservedFrom = reservedFrom;
		this.reservedTill = reservedTill;
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

	public Date getDateReserved() {
		return dateReserved;
	}

	public void setDateReserved(Date dateReserved) {
		this.dateReserved = dateReserved;
	}

	public Time getReservedFrom() {
		return reservedFrom;
	}

	public void setReservedFrom(Time reservedFrom) {
		this.reservedFrom = reservedFrom;
	}

	public Time getReservedTill() {
		return reservedTill;
	}

	public void setReservedTill(Time reservedTill) {
		this.reservedTill = reservedTill;
	}
}

package com.example.ClinicalSystem.model;


import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name" , nullable = false)
	private String name;

	@Column(name = "startdate" , nullable = false)
	private Date start;


	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name = "starttime", nullable = false )
	private Time startTime;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name = "endtime", nullable = false )
	private Time endTime;

	@Column(name="status",nullable = false)
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Column(name="class",nullable = false)
	@Enumerated(EnumType.STRING)
	private AppointmentClassification classification;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ExamType type;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Patient patient;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Doctor doctor;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private OR or;


	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Calendar calendar;

	@OneToOne(cascade = CascadeType.ALL)
	private Report report;

	public Appointment() {
		super();
	}

	public Appointment(Long id, Date start, ExamType type, Patient patient, Doctor doctor,
					   OR room, String name) {
		super();
		this.id = id;
		this.start = start;
		this.type = type;
		this.patient = patient;
		this.doctor = doctor;
		this.or = room;
		this.name = name;
	}

	public Appointment(long id, String name, Date start, Time startTime, Time endTime, AppointmentStatus status, AppointmentClassification classification){
		this.id = id;
		this.name = name;
		this.start = start;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.classification = classification;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExamType getType() {
		return type;
	}

	public void setType(ExamType type) {
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


	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OR getOr() {
		return or;
	}

	public void setOr(OR or) {
		this.or = or;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public AppointmentClassification getClassification() {
		return classification;
	}

	public void setClassification(AppointmentClassification classification) {
		this.classification = classification;
	}
}

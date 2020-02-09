package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="OpRoom")
public class OR {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = false)
	private int number;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name="starttime")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	private Time start;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
	@Column(name="endtime")
	private Time end;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ExamType examType;

	@OneToMany
	@JoinTable(name = "room_app", joinColumns = @JoinColumn(name="op_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="appointment_id", referencedColumnName = "id"))
	private Set<Appointment> appointments = new HashSet<Appointment>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "room_operations", joinColumns = @JoinColumn(name="or_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="operation_id", referencedColumnName = "id"))
	private Set<OperationRequest> operations = new HashSet<OperationRequest>();

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;

	@Version
	private Long version;


	public OR() {
		super();
	}

	public OR(Long id, int number, ExamType examType, Time start, Time end, Clinic clinic) {
		super();
		this.id = id;
		this.number = number;
		this.name = name;
		this.examType = examType;
		this.start = start;
		this.end = end;
		this.clinic = clinic;
	}

	public OR(Long id, int number, String name, ExamType examType) {
		super();
		this.number = number;
		this.name = name;
		this.examType = examType;
		this.id = id;
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

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public ExamType getExamType() {
		return examType;
	}

	public void setExamType(ExamType examType) {
		this.examType = examType;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Set<OperationRequest> getOperations() {
		return operations;
	}

	public void setOperations(Set<OperationRequest> operations) {
		this.operations = operations;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}

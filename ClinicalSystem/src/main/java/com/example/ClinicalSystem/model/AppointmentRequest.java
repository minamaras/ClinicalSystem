package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
public class AppointmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "startdate" , nullable = false)
    private java.sql.Date start;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Column(name = "starttime", nullable = false )
    private Time startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Column(name = "endtime", nullable = false )
    private Time endTime;

    @Column(name="appreqstatus")
    @Enumerated(EnumType.STRING)
    private AppointmentRequestStatus appointmentRequestStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ExamType type;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Doctor doctor;

    @Column(name="roomnum")
    private Long roomId;

    @Version
    private Long version;

    public AppointmentRequest() {
        super();
    }

    public AppointmentRequest(Long id, java.sql.Date start, ExamType type, Patient patient, Doctor doctor,
                              boolean hasHappend, OR room, String name) {
        super();
        this.id = id;
        this.start = start;
        this.type = type;
        this.patient = patient;
        this.doctor = doctor;
        this.name = name;
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


    public Date getStart() {
        return start;
    }

    public void setStart(java.sql.Date start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AppointmentRequestStatus getAppointmentRequestStatus() {
        return appointmentRequestStatus;
    }

    public void setAppointmentRequestStatus(AppointmentRequestStatus appointmentRequestStatus) {
        this.appointmentRequestStatus = appointmentRequestStatus;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomNumber) {
        this.roomId = roomNumber;
    }
}



package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.print.Doc;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OperationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "startdate" , nullable = false)
    private Date start;

    @Column(name = "isScheduled", nullable = false)
    private boolean isScheduled;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Column(name = "starttime", nullable = false )
    private Time startTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Column(name = "endtime", nullable = false )
    private Time endTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Patient patient;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_operations", joinColumns = @JoinColumn(name = "operations_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private OR or;

    public OperationRequest() {
        super();
    }

    public OperationRequest(long id, String name, Date start, Time startTime, Time endTime, Patient patient, boolean isScheduled){
        this.id = id;
        this.name = name;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patient = patient;
        this.isScheduled = isScheduled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public OR getOr() {
        return or;
    }

    public void setOr(OR or) {
        this.or = or;
    }
}

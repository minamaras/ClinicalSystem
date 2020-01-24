package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Appointment;

import java.sql.Time;
import java.util.Date;

public class AppointmentDTO {

    private Long id;
    private Date start;
    private Time startTime;
    private Time endTime;
    private String examTypeName;
    private String doctorEmail;
    private int roomNumber;
    private String name;

    public AppointmentDTO(Long id, Date start, Time startTime, Time endTime, String examTypeName, String doctorEmail, int roomNumber, String name) {
        this.id = id;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNumber = roomNumber;
        this.doctorEmail = doctorEmail;
        this.examTypeName = examTypeName;
        this.name = name;
    }

    public AppointmentDTO() {
        super();
    }

    public AppointmentDTO(Appointment a) {
        this(a.getId(), a.getStart(), a.getStartTime(), a.getEndTime(),a.getType().getName(), a.getDoctor().getEmail(), a.getOr().getNumber(), a.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


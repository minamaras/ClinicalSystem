package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.AppointmentClassification;
import com.example.ClinicalSystem.model.AppointmentStatus;

import java.sql.Time;
import java.util.Date;

public class UpcomingExamDTO {


    private DoctorDTO doctor;
    private String date;
    private Time startTime;
    private Time endTime;
    private ExamTypeDTO type;
    private int roomNumber;
    private Long id;


    public UpcomingExamDTO(Date start, DoctorDTO doctor, String date, Time startTime, Time endTime, ExamTypeDTO type) {
        this.doctor = doctor;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    public UpcomingExamDTO(){}



    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public ExamTypeDTO getType() {
        return type;
    }

    public void setType(ExamTypeDTO type) {
        this.type = type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

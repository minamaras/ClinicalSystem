package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.AppointmentRequest;

import java.sql.Time;
import java.util.Date;

public class AppointmentRequestDTO {

    private Long id;
    private Date start;
    private String date;
    private String examTypeName;
    private String name;
    private Time startTime;
    private Time endTime;
    private Long doctorid;

    public AppointmentRequestDTO(Long id, Date start, String examTypeName, String doctorEmail, int roomNumber, String name) {
        this.id = id;
        this.start = start;
        this.examTypeName = examTypeName;
        this.name = name;
    }

    public AppointmentRequestDTO() {
        super();
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

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Long doctorid) {
        this.doctorid = doctorid;
    }
}


package com.example.ClinicalSystem.DTO;

import java.sql.Time;
import java.util.List;

public class OperationCalendarDTO {

    private long id;
    private Time startTime;
    private Time endTime;
    private String date;
    private String patientName;
    private String patientLastname;
    private String name;
    private List<String> doctorNames;

    public OperationCalendarDTO(long id, Time startTime, Time endTime, String date, String patientName, String patientLastname, String name) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.patientName = patientName;
        this.patientLastname = patientLastname;
        this.name = name;
    }

    public OperationCalendarDTO(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLastname() {
        return patientLastname;
    }

    public void setPatientLastname(String patientLastname) {
        this.patientLastname = patientLastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDoctorNames() {
        return doctorNames;
    }

    public void setDoctorNames(List<String> doctorNames) {
        this.doctorNames = doctorNames;
    }
}

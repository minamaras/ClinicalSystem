package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.OperationRequest;

import java.sql.Date;
import java.sql.Time;

public class OperationRequestDTO {

    private long id;
    private String name;
    private String start;
    private Time startTime;
    private Time endTime;
    private String patientemail;

    public OperationRequestDTO(){

    }

    public OperationRequestDTO(long id, String name, String start, Time startTime, Time endTime, String patientemail) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientemail = patientemail;
    }

    public OperationRequestDTO(OperationRequest or){
        this(or.getId(),or.getName(),or.getStart().toString().substring(0,10),or.getStartTime(),or.getEndTime(),or.getPatient().getEmail());
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
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

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }
}

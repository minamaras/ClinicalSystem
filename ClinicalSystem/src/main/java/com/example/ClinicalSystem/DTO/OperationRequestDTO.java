package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.OperationRequest;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class OperationRequestDTO {

    private long id;
    private String name;
    private String start;
    private String date;
    private int roomNumber;
    private Time startTime;
    private Time endTime;
    private String patientemail;
    private boolean isScheduled;
    private List<DoctorDTO> doctorDTOS = new ArrayList<>();
    private List<String> doctorNames = new ArrayList<>();

    public OperationRequestDTO(){

    }

    public OperationRequestDTO(long id, String name, String start, String patientemail, boolean isScheduled) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.patientemail = patientemail;
        this.isScheduled = isScheduled;

    }

    public OperationRequestDTO(long id, String name, String start, String patientemail, boolean isScheduled, List doctorNames, int roomNumber, Time startTime) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.patientemail = patientemail;
        this.isScheduled = isScheduled;
        this.doctorNames = doctorNames;
        this.roomNumber = roomNumber;

    }

    public OperationRequestDTO(OperationRequest or){
        this(or.getId(),or.getName(),or.getStart().toString().substring(0,10),or.getPatient().getEmail(), or.isScheduled());

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

    public boolean isScheduled() {
        return isScheduled;
    }

    public void setScheduled(boolean scheduled) {
        isScheduled = scheduled;
    }

    public List<DoctorDTO> getDoctorDTOS() {
        return doctorDTOS;
    }

    public void setDoctorDTOS(List<DoctorDTO> doctorDTOS) {
        this.doctorDTOS = doctorDTOS;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getDoctorNames() {
        return doctorNames;
    }

    public void setDoctorNames(List<String> doctorNames) {
        this.doctorNames = doctorNames;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}

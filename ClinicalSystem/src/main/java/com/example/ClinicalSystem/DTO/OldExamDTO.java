package com.example.ClinicalSystem.DTO;

import java.sql.Time;


public class OldExamDTO {

    private Long id;
    private DoctorDTO doctor;
    private int roomNumber;
    private String name;
    private String date;
    private Time startTime;
    private Time endTime;
    private ExamTypeDTO type;
    private ClinicDTO clinic;



    public OldExamDTO(Long id, DoctorDTO doctor, int roomNumber, String name, String date, Time startTime, Time endTime, ExamTypeDTO type, ClinicDTO clinic) {
        this.id = id;
        this.doctor = doctor;
        this.roomNumber = roomNumber;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.clinic = clinic;
    }

    public OldExamDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
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

    public ClinicDTO getClinic() {
        return clinic;
    }

    public void setClinic(ClinicDTO clinic) {
        this.clinic = clinic;
    }
}

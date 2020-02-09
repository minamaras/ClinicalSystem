package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentClassification;
import com.example.ClinicalSystem.model.AppointmentStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Time;
import java.util.Date;

public class AppointmentDTO {

    private long id;
    private Date start;
    private String examTypeName;
    private String doctorEmail;
    private Long doctorid;
    private int roomNumber;
    private String name;
    private String date;
    private Time startTime;
    private Time endTime;
    private ExamTypeDTO type;
    private AppointmentStatus status;
    private AppointmentClassification classification;
    private String patientemail;

    private String patientName;
    private String patientLastname;


    public AppointmentDTO(long id, Date start, String examTypeName, String doctorEmail, int roomNumber, String name) {
        this.id = id;
        this.start = start;
        this.roomNumber = roomNumber;
        this.doctorEmail = doctorEmail;
        this.examTypeName = examTypeName;
        this.name = name;
    }

    public AppointmentDTO(long id, String name, Date start, Time startTime, Time endTime){
        this.id = id;
        this.name = name;
        this.start = start;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AppointmentDTO() {
        super();
    }

    public AppointmentDTO(Appointment a) {
        this(a.getId(), a.getStart(), a.getType().getName(), a.getDoctor().getEmail(), a.getOr().getNumber(), a.getName());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentClassification getClassification() {
        return classification;
    }

    public void setClassification(AppointmentClassification classification) {
        this.classification = classification;
    }

    public Long getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(Long doctorid) {
        this.doctorid = doctorid;
    }
    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
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
}

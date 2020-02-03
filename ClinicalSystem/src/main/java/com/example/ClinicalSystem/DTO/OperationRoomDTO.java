package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.model.OR;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OperationRoomDTO {

    private Long id;
    private int number;
    private String name;
    private Date dateReserved;
    private Time start;
    private Time end;
    private ExamTypeDTO examType;
    private Set<AppointmentDTO> appointments = new HashSet<>();
    private Long clinicid;
    private String clinicname;

    public OperationRoomDTO(Long id, int number, String name, Time start, Time end, ExamTypeDTO examType) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.dateReserved = dateReserved;
        this.start = start;
        this.end = end;
        this.examType = examType;
    }

    public OperationRoomDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(Date dateReserved) {
        this.dateReserved = dateReserved;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public ExamTypeDTO getExamType() {
        return examType;
    }

    public void setExamType(ExamTypeDTO examType) {
        this.examType = examType;
    }

    public Set<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public Long getClinicid() {
        return clinicid;
    }

    public void setClinicid(Long clinicid) {
        this.clinicid = clinicid;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

}

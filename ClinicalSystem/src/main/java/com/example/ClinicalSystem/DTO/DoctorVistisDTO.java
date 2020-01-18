package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.MedicalRecord;

import java.sql.Date;

public class DoctorVistisDTO {

    private int id;
    private Date visitDate;
    private String visitDescription;
    //private MedicalRecordDTO medicalRecord;
    private DoctorDTO doctor;
    private String somedate;

    public DoctorVistisDTO(int id, Date visitDate, String visitDescription,String somedate) {
        this.id = id;
        this.visitDate = visitDate;
        this.visitDescription = visitDescription;
        this.somedate= somedate;
    }

    public DoctorVistisDTO(){}

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitDescription() {
        return visitDescription;
    }

    public void setVisitDescription(String visitDescription) {
        this.visitDescription = visitDescription;
    }

    //public MedicalRecordDTO getMedicalRecord() {
       // return medicalRecord;
    //}

    //public void setMedicalRecord(MedicalRecordDTO medicalRecord) {
        //this.medicalRecord = medicalRecord;
    //}


    public String getSomedate() {
        return somedate;
    }

    public void setSomedate(String somedate) {
        this.somedate = somedate;
    }
}

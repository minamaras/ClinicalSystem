package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;


@Entity
public class DoctorVisits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "visitdate", nullable = false)
    private Date visitDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_visits_doctor",
            joinColumns =
                    { @JoinColumn(name = "visit_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "doctor_id", referencedColumnName = "id") })
    private Doctor doctor;

    @Column(name = "visitdescription")
    private String visitDescription;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public DoctorVisits(){

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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getVisitDescription() {
        return visitDescription;
    }

    public void setVisitDescription(String visitDescription) {
        this.visitDescription = visitDescription;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
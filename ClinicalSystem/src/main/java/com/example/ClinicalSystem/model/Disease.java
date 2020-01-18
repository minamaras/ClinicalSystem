package com.example.ClinicalSystem.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "diseases")
    private Set<MedicalRecord> records = new HashSet<>();

    @Column(name="diseasename")
    private String name;

    @Column(name="diseasedescription")
    private String diseaseDescription;

    public Disease() {
    }

    public Set<MedicalRecord> getRecord() {
        return records;
    }

    public void setRecord(Set<MedicalRecord> record) {
        this.records = record;
    }

    public String getDiseaseDescription() {
        return diseaseDescription;
    }

    public void setDiseaseDescription(String diseaseDescription) {
        this.diseaseDescription = diseaseDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

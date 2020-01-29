package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.MedicalRecord;

public class MedicalRecordDTO {

    private Long id;
    private String additional;
    private String allergies;
    private String bloodtype;
    private String measures;
    private String eyes;
    private String patientemail;
    private String patientName;


    public MedicalRecordDTO(){

    }

    public MedicalRecordDTO(MedicalRecord medicalRecord){
        this.id = medicalRecord.getId();
        this.additional = medicalRecord.getAdditional();
        this.allergies = medicalRecord.getAllergies();
        this.bloodtype = medicalRecord.getBloodtype();
        this.measures = medicalRecord.getMeasures();
        this.eyes = medicalRecord.getEyes();
        this.patientemail = medicalRecord.getPatient().getEmail();
        this.patientName = medicalRecord.getPatient().getName();
    }

    public MedicalRecordDTO(Long id, String additional, String allergies, String bloodtype, String measures, String patientemail) {
        this.id = id;
        this.additional = additional;
        this.allergies = allergies;
        this.bloodtype = bloodtype;
        this.measures = measures;
        this.eyes = eyes;
        this.patientemail = patientemail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
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
}

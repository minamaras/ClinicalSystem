package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO {

    private long id;
    private String text;
    private String diagnosisName;
    private String doctoremail;
    private String patientemail;
    private boolean isEditable;
    private boolean editThisOne;

    public ReportDTO(String text, String diagnosis, String doctoremail, String patientemail) {
        this.text = text;
        this.diagnosisName = diagnosis;
        this.doctoremail = doctoremail;
        this.isEditable = false;
        this.editThisOne = false;
    }

    public ReportDTO(long id, String text, String diagnosis){
        this.id = id;
        this.text = text;
        this.diagnosisName = diagnosis;
    }

    public ReportDTO(String text, String diagnosisName, String patientemail){
        this.text = text;
        this.diagnosisName = diagnosisName;
        this.patientemail = patientemail;
        this.isEditable = false;
        this.editThisOne = false;
    }

    public ReportDTO(){
        super();
    }

    public ReportDTO(Report report){
        this.id = report.getId();
        this.text = report.getText();
        if(report.getDiagnosis() != null) {
            this.diagnosisName = report.getDiagnosis().getName();
        }
        this.doctoremail = report.getDoctor().getEmail();
        this.patientemail = report.getMedicalRecord().getPatient().getEmail();
        this.isEditable = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDoctoremail() {
        return doctoremail;
    }

    public void setDoctoremail(String doctoremail) {
        this.doctoremail = doctoremail;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isEditThisOne() {
        return editThisOne;
    }

    public void setEditThisOne(boolean editThisOne) {
        this.editThisOne = editThisOne;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDTO {

    private String text;
    private String diagnosisName;
    private String doctoremail;
    private String patientemail;

    public ReportDTO(String text, String diagnosis, String doctoremail, String patientemail) {
        this.text = text;
        this.diagnosisName = diagnosis;
        this.doctoremail = doctoremail;
    }

    public ReportDTO(String text, String diagnosisName, String patientemail){
        this.text = text;
        this.diagnosisName = diagnosisName;
        this.patientemail = patientemail;
    }

    public ReportDTO(){
        super();
    }

    public ReportDTO(Report report){
        this.text = report.getText();
        if(report.getDiagnosis() != null) {
            this.diagnosisName = report.getDiagnosis().getName();
        }
        this.doctoremail = report.getDoctor().getEmail();
        this.patientemail = report.getMedicalRecord().getPatient().getEmail();
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
}

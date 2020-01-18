package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.MedicalRecord;
import com.example.ClinicalSystem.model.Patient;

import java.util.HashSet;
import java.util.Set;

public class MedicalRecordDTO {

    private int id;
    private PatientDTO patient;
    private Set<DoctorVistisDTO> doctorVisits;
    private String medicalHistory;
    private Set<DoctorDTO> doctors;
    private Set<String> diseases = new HashSet<>();


    public MedicalRecordDTO(int id, PatientDTO patient, Set<DoctorVistisDTO> doctorVisits, String medicalHistory, Set<DoctorDTO> doctors) {
        this.id = id;
        this.patient = patient;
        this.doctorVisits = doctorVisits;
        this.medicalHistory = medicalHistory;
        this.doctors = doctors;
    }

    public MedicalRecordDTO (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public Set<DoctorVistisDTO> getDoctorVisits() {
        return doctorVisits;
    }

    public void setDoctorVisits(Set<DoctorVistisDTO> doctorVisits) {
        this.doctorVisits = doctorVisits;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public Set<DoctorDTO> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<DoctorDTO> doctors) {
        this.doctors = doctors;
    }

    public Set<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(Set<String> diseases) {
        this.diseases = diseases;
    }
}

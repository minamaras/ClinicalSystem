package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ReportDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.ReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MedicalRecordService medicalRecordService;

    public Report findById(long id){
        return reportRepository.findById(id);
    }

    public Report save(Report report) { return reportRepository.save(report); }

    public boolean addNew(ReportDTO reportDTO, Principal p) {
        Doctor doctor = (Doctor) userService.findByUsername(p.getName());
        Patient patient = patientService.findPatient(reportDTO.getPatientemail());

        if(patient == null){
            return false;
        }

        Report report = new Report();

        if(reportDTO.getDiagnosisName() != "None") {
            Diagnosis diagnosis = diagnosisService.findByName(reportDTO.getDiagnosisName());
            report.setDiagnosis(diagnosis);
        } else {
            report.setDiagnosis(null);
        }

        MedicalRecord medicalRecord = medicalRecordService.findById(patient.getMedicalRecord().getId());


        report.setDoctor(doctor);
        report.setMedicalRecord(medicalRecord);
        report.setText(reportDTO.getText());
        save(report);

        medicalRecord.getReports().add(report);
        medicalRecordService.saveRecord(medicalRecord);

        return true;

    }
}

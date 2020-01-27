package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.MedicalRecordDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.DTO.ReportDTO;
import com.example.ClinicalSystem.model.MedicalRecord;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.Report;
import com.example.ClinicalSystem.service.MedicalRecordService;
import com.example.ClinicalSystem.service.PatientService;
import com.example.ClinicalSystem.service.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/info")
    @PreAuthorize("hasAnyAuthority('NURSE','DOCTOR')")
    public ResponseEntity<List<ReportDTO>> allReports(@RequestBody String patientemail) {

        Patient patient = patientService.findPatient(patientemail);

        MedicalRecord medicalRecord = medicalRecordService.findById(patient.getMedicalRecord().getId());
        Set<Report> reports = medicalRecord.getReports();
        List<ReportDTO> reportsDTO = new ArrayList<>();

        for(Report r: reports){
            ReportDTO rep = new ReportDTO(r);
            reportsDTO.add(rep);
        }

        //  return new ResponseEntity<>(modelMapper.map(medicalRecord, MedicalRecordDTO.class), HttpStatus.OK);
        return ResponseEntity.ok(reportsDTO);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/addreport")
    @PreAuthorize("hasAnyAuthority('DOCTOR','NURSE')")
    public ResponseEntity<?> addReport(@RequestBody ReportDTO reportdto, Principal p) {

        boolean isAdded = reportService.addNew(reportdto, p);
        if(!isAdded){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}

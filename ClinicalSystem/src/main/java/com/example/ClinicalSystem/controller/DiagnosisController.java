package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @RequestMapping(method = RequestMethod.POST, value = "/adddiagnosis")
    @PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
    public ResponseEntity<Diagnosis> addDiagnosis(@RequestBody Diagnosis diagnosis) {

        diagnosisService.addDiagnosis(diagnosis);
        return new ResponseEntity<>(diagnosis, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/alldiagnosis")
    @PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
    public ResponseEntity<List<Diagnosis>> getAllDiagnosis() {

        List<Diagnosis> diagnosis = diagnosisService.findAll();

        return new ResponseEntity<>(diagnosis, HttpStatus.OK);
    }


}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.DiagnosisNamesDTO;
import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @RequestMapping(method = RequestMethod.POST, value = "/adddiagnosis")
    @PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
    public ResponseEntity<?> addDiagnosis(@RequestBody Diagnosis diagnosis) {

        boolean isAdded = diagnosisService.addDiagnosis(diagnosis);
        if(!isAdded){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/alldiagnosis")
    @PreAuthorize("hasAnyAuthority('CLINICALCENTREADMIN','DOCTOR','NURSE')")
    public ResponseEntity<List<Diagnosis>> getAllDiagnosis() {

        List<Diagnosis> diagnosis = diagnosisService.findAll();

        return new ResponseEntity<>(diagnosis, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/alldiagnosisnames")
    @PreAuthorize("hasAnyAuthority('DOCTOR','NURSE')")
    public ResponseEntity<List<DiagnosisNamesDTO>> getAllDiagnosisNames() {

        List<Diagnosis> diagnosis = diagnosisService.findAll();
        List<DiagnosisNamesDTO> diagnosisNamesDTOS = new ArrayList<>();

        for(Diagnosis diag : diagnosis){
            DiagnosisNamesDTO dNamesDTO = new DiagnosisNamesDTO(diag.getName());
            diagnosisNamesDTOS.add(dNamesDTO);
        }


        return new ResponseEntity<>(diagnosisNamesDTOS, HttpStatus.OK);
    }


}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.DiagnosisNamesDTO;
import com.example.ClinicalSystem.DTO.MedicationNamesDTO;
import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @RequestMapping(method = RequestMethod.POST, value = "/addmedication")
    public ResponseEntity<?> addMedication(@RequestBody Medication medication) {

        boolean isAdded = medicationService.addMedication(medication);
        if(!isAdded){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @RequestMapping(method = RequestMethod.GET, value = "/allmedications")
    public ResponseEntity<List<Medication>> getAllMedication() {

        List<Medication> medications = medicationService.findAll();

        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allmedicationsnames")
    @PreAuthorize("hasAnyAuthority('DOCTOR','NURSE')")
    public ResponseEntity<List<MedicationNamesDTO>> getAllMedicationNames() {

        List<Medication> medications = medicationService.findAll();
        List<MedicationNamesDTO> medicationNamesDTOS = new ArrayList<>();

        for(Medication med : medications){
            MedicationNamesDTO mDTO = new MedicationNamesDTO(med.getName());
            medicationNamesDTOS.add(mDTO);
        }


        return new ResponseEntity<>(medicationNamesDTOS, HttpStatus.OK);
    }
}

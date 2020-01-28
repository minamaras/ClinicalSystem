package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.MedicalRecordDTO;
import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.ReportDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.service.MedicalRecordService;
import com.example.ClinicalSystem.service.PatientService;
import com.example.ClinicalSystem.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/medicalrecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(method = RequestMethod.POST, value = "/info")
    @PreAuthorize("hasAnyAuthority('NURSE','DOCTOR','PATIENT')")
    public ResponseEntity<MedicalRecordDTO> medicalRecordInfo(@RequestBody String patientemail) {

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole() == Role.PATIENT) {
            Patient loggedinpatient = patientService.findPatient(user.getEmail());
            MedicalRecord medicalRecord = medicalRecordService.findById(loggedinpatient.getMedicalRecord().getId());
            MedicalRecordDTO dto = new MedicalRecordDTO(medicalRecord);
            return ResponseEntity.ok(dto);
        }

        Patient patient = patientService.findPatient(patientemail);

        MedicalRecord medicalRecord = medicalRecordService.findById(patient.getMedicalRecord().getId());

        MedicalRecordDTO dto = new MedicalRecordDTO(medicalRecord);
      //  return new ResponseEntity<>(modelMapper.map(medicalRecord, MedicalRecordDTO.class), HttpStatus.OK);
        return ResponseEntity.ok(dto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change/basic")
    @PreAuthorize("hasAnyAuthority('NURSE','DOCTOR')")
    public ResponseEntity changeBasicInfo(@RequestBody MedicalRecordDTO medicalRecordDTO) {

        boolean isChanged = medicalRecordService.changeBasicInfo(medicalRecordDTO);

        if(isChanged) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }








}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.MedicalRecordDTO;
import com.example.ClinicalSystem.model.MedicalRecord;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.MedicalRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value ="api/medicalrecord")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/getrecord")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord() {

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        MedicalRecordDTO medicalRecordDTO =modelMapper.map(medicalRecordService.findRecord(user.getId()), MedicalRecordDTO.class);

        return new ResponseEntity<>(medicalRecordDTO, HttpStatus.OK);
    }
}


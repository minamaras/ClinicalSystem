package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<?> getAll() {
        List<HolidayDTO> holidayDTOS = holidayService.findAll();

        return new ResponseEntity<>(holidayDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/makerequest")
    @PreAuthorize("hasAnyAuthority('NURSE','DOCTOR')")
    public ResponseEntity<?> makeRequest(@RequestBody HolidayDTO holidayDTO, Principal p) {

        boolean isApproved = holidayService.request(p, holidayDTO);


        if(!isApproved) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @RequestMapping(method = RequestMethod.POST, value = "/makerequest/{nurseid}")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity<?> makeRequest(@PathVariable String nurseid, @RequestBody HolidayDTO holidayDTO) {

        boolean isApproved = holidayService.request(nurseid, holidayDTO);

        if(!isApproved) {
            return new ResponseEntity<>("Request with this start date or reason already exists!", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Success!", HttpStatus.OK);
        }
    }
}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.service.AppointmentRequestService;
import com.example.ClinicalSystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/appointmentrequest")
public class AppointmentRequestController {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @RequestMapping(method = RequestMethod.POST, value = "/saveappointmentrequest")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<?> saveAppointmentRequest(@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws ParseException, UnsupportedEncodingException {

        boolean issaved = appointmentRequestService.saveAppointmentRequest(appointmentRequestDTO);
        if(issaved) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    @PreAuthorize("hasAnyAuthority('CLINICADMIN')")
    public ResponseEntity<?> getAll() {
        List<AppointmentRequest> requests = appointmentRequestService.findAll();

        return new ResponseEntity<>(requests, HttpStatus.OK);
    }


}

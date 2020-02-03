package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
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

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<List<AppointmentRequestDTO>> getAll() throws ParseException {

       List<AppointmentRequestDTO> appointmentRequestDTOS = appointmentRequestService.findAll();

        return new ResponseEntity<>(appointmentRequestDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getone/{id}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity <AppointmentRequestDTO> getOnebyId(@PathVariable String id) throws ParseException {

        AppointmentRequestDTO appointmentRequestDTO = appointmentRequestService.findById(Long.parseLong(id));

        return new ResponseEntity<>(appointmentRequestDTO, HttpStatus.OK);
    }

}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OldExamDTO;
import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(method = RequestMethod.GET, value = "/allpredefined")
    @PreAuthorize("hasAnyAuthority('CLINICADMIN','PATIENT')")
    public ResponseEntity<List<AppointmentDTO>> getAll() {

        List<AppointmentDTO> predefined = appointmentService.findAllPredefined();

        return new ResponseEntity<>(predefined, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savepredefined")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<AppointmentDTO> savePredefiend(@RequestBody AppointmentDTO appointmentDTO) throws ParseException {

        if(appointmentService.savePredefined(appointmentDTO))
            return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);

        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) throws ParseException {

        if(appointmentService.saveAppointment(appointmentDTO)){
            return  new ResponseEntity<>(HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<Set<AppointmentDTO>> getAllPatientsExams() {

        Set<AppointmentDTO> exams = appointmentService.getAllExams();

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allold")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<Set<OldExamDTO>> getAllPatientsExamsOld() {

        Set<OldExamDTO> exams = appointmentService.getAllExamsOld();

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

}

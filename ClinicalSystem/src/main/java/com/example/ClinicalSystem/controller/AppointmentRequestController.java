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

    @RequestMapping(method = RequestMethod.POST, value = "/updateappointmentrequest/{roomid}/{examdate}/{examtime}/{endtime}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<?> createAppointmentFromRequest(@PathVariable("roomid") String roomId, @PathVariable("examdate") String examdate, @PathVariable("examtime") String examtime, @PathVariable("endtime") String endtime,@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws ParseException {

        if(appointmentRequestService.IsCreated(roomId, examdate, examtime, endtime, appointmentRequestDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "/sendrequest/{doctoremail}/{examdate}/{examtime}/{endtime}/{id}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<?> sendRequestForAppointment(@PathVariable("doctoremail") String roomId, @PathVariable("examdate") String examdate, @PathVariable("examtime") String examtime, @PathVariable("endtime") String endtime, @PathVariable("id") String id,@RequestBody AppointmentRequestDTO appointmentRequestDTO) throws ParseException {

        if(appointmentRequestService.sendRequest(roomId, examdate, examtime, endtime, appointmentRequestDTO, id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    @RequestMapping(method = RequestMethod.GET, value = "/allmyexams")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<AppointmentRequestDTO>> getMyExams() throws ParseException {

        List<AppointmentRequestDTO> appointmentRequestDTOS = appointmentRequestService.findMyExams();

        return new ResponseEntity<>(appointmentRequestDTOS, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/scheduleanother/{doctor}/{examdate}/{patient}/{startexam}/{endexam}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<?> scheduleAnotherAppointmentRequest(@PathVariable("doctor") int doctorId, @PathVariable("examdate") String examDate, @PathVariable("patient") String patientEmail, @PathVariable("startexam") String startExam, @PathVariable("endexam") String endExam, AppointmentDTO appointmentDTO) throws ParseException {

        if(appointmentRequestService.scheduleAnother(doctorId, examDate, patientEmail, startExam, endExam)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

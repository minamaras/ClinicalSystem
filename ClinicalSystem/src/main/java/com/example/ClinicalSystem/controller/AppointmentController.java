package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.service.AppointmentService;
import com.sun.mail.iap.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
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

        if (appointmentService.savePredefined(appointmentDTO))
            return new ResponseEntity<>(appointmentDTO, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) throws Exception {

        if (appointmentService.saveNewAppointment(appointmentDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<Set<UpcomingExamDTO>> getAllPatientsExams() {

        Set<UpcomingExamDTO> exams = appointmentService.getAllExams();

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allold")
    @PreAuthorize("hasAnyAuthority('PATIENT')")
    public ResponseEntity<Set<OldExamDTO>> getAllPatientsExamsOld() {

        Set<OldExamDTO> exams = appointmentService.getAllExamsOld();

        return new ResponseEntity<>(exams, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/startappoint/{id}")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    public ResponseEntity<AppointmentDTO> getOneExam(@PathVariable long id) {

        AppointmentDTO appDTO = appointmentService.getOneAppoint(id);

        return new ResponseEntity<>(appDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/endappoint/{id}")
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    public ResponseEntity endExam(@PathVariable long id) {

        boolean isDone = appointmentService.endAppoint(id);

        if (isDone) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getcurrent")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<DoctorDTO> getCurrent() {

        DoctorDTO doctorDTO = appointmentService.currentDoctor();

        if (doctorDTO != null)
            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(method = RequestMethod.GET, value = "/saverequesttoappointment/{id}")
    public ResponseEntity<?> saveRequestToAppointment(@PathVariable String id) throws URISyntaxException {

        boolean issaved = appointmentService.saveFromReqToAppointment(id);
        if(issaved) {
            URI newUri = new URI("http://localhost:3000/confirmedappreq");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(newUri);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }


    @RequestMapping(method = RequestMethod.GET, value = "/declinerequesttoappointment/{id}")
    public ResponseEntity<?> declineRequestToAppointment(@PathVariable String id) throws URISyntaxException {

        boolean changedstatus = appointmentService.declineAppRequest(id);

        if(changedstatus){
            URI newUri = new URI("http://localhost:3000/declinedappreq");
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(newUri);
            return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
        }else{
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/createappointment/{roomid}/{examdate}/{examtime}/{endtime}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<?> createAppointmentFromRequest(@PathVariable("roomid") String roomId, @PathVariable("examdate") String examdate, @PathVariable("examtime") String examtime, @PathVariable("endtime") String endtime, @RequestBody AppointmentRequestDTO appointmentRequestDTO) throws ParseException {

        if (appointmentService.IsCreated(roomId, examdate, examtime, endtime, appointmentRequestDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}

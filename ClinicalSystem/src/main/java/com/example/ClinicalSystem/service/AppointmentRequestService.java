package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRequestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AppointmentRequestService {


    @Autowired
    private AppointmentRequestRepository appointmentRequestRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ExamTypeService examTypeService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClinicService clinicService;


    public boolean saveAppointmentRequest(AppointmentRequestDTO appointmentRequestDTO) throws ParseException, UnsupportedEncodingException {

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        Patient p = patientService.findPatient(user.getEmail());

        AppointmentRequest appointmentRequest = modelMapper.map(appointmentRequestDTO, AppointmentRequest.class);
        appointmentRequest.setPatient(p);

        String startDate=appointmentRequestDTO.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        appointmentRequest.setStart(finaldate);

        String decoded = URLDecoder.decode(appointmentRequestDTO.getExamTypeName(), "UTF-8");
        ExamType examType =examTypeService.findOne(decoded);
        if(examType != null){
            appointmentRequest.setType(examType);
            java.sql.Time startTime = java.sql.Time.valueOf(appointmentRequest.getStartTime().toString());
            LocalTime localtime = startTime.toLocalTime();
            localtime = localtime.plusMinutes(examType.getDuration());
            Time endTime= Time.valueOf(localtime);
            appointmentRequest.setEndTime(endTime);
        }

        Doctor doctor = doctorService.findOneById(appointmentRequestDTO.getDoctorid());

        if(doctor != null){
            appointmentRequest.setDoctor(doctor);
            appointmentRequestDTO.setDoctorEmail(doctor.getEmail());
        }

        Clinic clinic = doctor.getClinic();
        List<ClinicAdmin> admins = new ArrayList<>();
        for(ClinicAdmin ca : clinic.getClinicAdmins()){
            admins.add(ca);
        }

        int randomNumberAdmin = (int)(Math.random() * admins.size());


        //provera da li postoji vec taj u bazi

        AppointmentRequest reqpatient = appointmentRequestRepository.findByPatient(p);
        AppointmentRequest reqdate = appointmentRequestRepository.findByStart((Date) appointmentRequest.getStart());
        AppointmentRequest reqtime = appointmentRequestRepository.findByStartTime(appointmentRequest.getStartTime());
        AppointmentRequest reqtype = appointmentRequestRepository.findByType(appointmentRequest.getType());


        if(reqpatient != null && reqdate != null & reqtime != null && reqtype != null){
            return false;
        }

        if (appointmentRequestRepository.save(appointmentRequest) != null) {

            try {
                emailService.sendAdminNotificaitionAsync(admins.get(randomNumberAdmin),p,finaldate,appointmentRequest.getStartTime(),appointmentRequest.getEndTime(),appointmentRequest.getType());
            } catch (Exception e) {
                return false;
            }

            return true;
        } else {
            return false;


        }
    }

    public List<AppointmentRequestDTO> findAll() throws ParseException {
        List<AppointmentRequest> requests = appointmentRequestRepository.findAll();

        List<AppointmentRequestDTO> appointmentRequestDTOS = new ArrayList<>();
        for (AppointmentRequest c : requests) {
            AppointmentRequestDTO appointmentRequestDTO =modelMapper.map(c,AppointmentRequestDTO.class);

            appointmentRequestDTO.setStart(c.getStart());
            appointmentRequestDTO.setStartTime(c.getStartTime());
            appointmentRequestDTO.setEndTime(c.getEndTime());

            appointmentRequestDTOS.add(appointmentRequestDTO);
        }

        return appointmentRequestDTOS;
    }
}




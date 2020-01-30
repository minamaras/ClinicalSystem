package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRepository;
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
import java.lang.reflect.Type;
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
    private AppointmentRepository appointmentRepository;

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

    @Autowired
    private OperationRoomService operationRoomService;


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
        appointmentRequestDTO.setDate(startDate.toString().substring(0,10));

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
            String startDate=c.getDate();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = sdf1.parse(startDate);
            java.sql.Date finaldate = new java.sql.Date(date.getTime());
            c.setStart(finaldate);
            appointmentRequestDTOS.add(modelMapper.map(c,AppointmentRequestDTO.class));

        }

        return appointmentRequestDTOS;
    }

    public List<OperationRoomDTO> checkAvailableRooms(AppointmentRequestDTO appointmentRequestDTO) {

        AppointmentRequest appointmentRequest = modelMapper.map(appointmentRequestDTO, AppointmentRequest.class);

        List<Appointment> appointments = appointmentRepository.findAll();

        for(Appointment a : appointments) {
            //ako je u pitanju pregled koji je istog dana kao i onaj u zahtevu
            if(a.getStart().compareTo(appointmentRequest.getStart()) == 0) {
                //ako je u pitanju isto vreme
                if(a.getStartTime() == appointmentRequest.getStartTime() && a.getEndTime() == appointmentRequest.getEndTime()){
                    //mora da se definise druga soba i nikako ista zato moramo iz liste soba da izbacimo tu sobu
                    List<OR> rooms = operationRoomService.findAllRooms();
                    for(OR or : rooms) {
                        if(a.getOr().getNumber() == or.getNumber()) {
                            rooms.remove(or);
                        }
                    }

                    List<OperationRoomDTO> operationRoomDTOS = new ArrayList<>();
                    for (OR c : rooms) {
                        operationRoomDTOS.add(modelMapper.map(c,OperationRoomDTO.class));
                    }

                    return operationRoomDTOS;
                 //ako pocinje i zavrsava se pre rezervacije sale
                } else if(appointmentRequest.getStartTime().compareTo(a.getStartTime()) < 0 && appointmentRequest.getEndTime().compareTo(a.getEndTime()) < 0){
                    //ne treba da se proverava sala, moze bilo koja
                    //odobri se
                    List<OR> rooms = operationRoomService.findAllRooms();
                    List<OperationRoomDTO> operationRoomDTOS = new ArrayList<>();

                    for (OR c : rooms) {
                        operationRoomDTOS.add(modelMapper.map(c,OperationRoomDTO.class));
                    }

                    return operationRoomDTOS;
                  //ako pocinje posle zavrsetka rezervacije
                } else if(appointmentRequest.getStartTime().compareTo(a.getEndTime()) > 0) {
                    //ne treba da se proverava i menja sala
                    //odobri se
                    List<OR> rooms = operationRoomService.findAllRooms();
                    List<OperationRoomDTO> operationRoomDTOS = new ArrayList<>();

                    for (OR c : rooms) {
                        operationRoomDTOS.add(modelMapper.map(c,OperationRoomDTO.class));
                    }

                    return operationRoomDTOS;
                 //ako je pocetak pre rezervacije, ali je kraj tokom rezervacije
                } else if(appointmentRequest.getStartTime().compareTo(a.getStart()) < 0 && appointmentRequest.getEndTime().compareTo(a.getStart()) > 0) {
                    //mora da se nadje druga soba
                    List<OR> rooms = operationRoomService.findAllRooms();
                    for(OR or : rooms) {
                        if(a.getOr() == or) {
                            rooms.remove(or);
                        }
                    }
                    List<OperationRoomDTO> operationRoomDTOS = new ArrayList<>();

                    for (OR c : rooms) {
                        operationRoomDTOS.add(modelMapper.map(c,OperationRoomDTO.class));
                    }

                    return operationRoomDTOS;
                }
            }

        }

        return null;
    }



}




package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ExamTypeService examTypeService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private OperationRoomService operationRoomService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    public boolean saveAppointment(AppointmentDTO appointmentDTO){

        //fsdgsdgs

        if(appointmentRepository.findById(appointmentDTO.getId()) == null){
            return  false;
        }else {

            Optional<Appointment> appointmentop = appointmentRepository.findById(appointmentDTO.getId());
            Appointment appointment = appointmentop.get();

            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) a.getPrincipal();

            if(user.getRole() == Role.PATIENT) {
                Patient loggedinpatient = patientService.findPatient(user.getEmail());
                appointment.setPatient(loggedinpatient);
            }

            appointment.setClassification(AppointmentClassification.NORMAL);
            appointment.setStatus(AppointmentStatus.SHEDULED);


            appointmentRepository.save(appointment);
            return true;
    }

    }

    public List<AppointmentDTO> findAllPredefined() {

        List<Appointment> predefined = appointmentRepository.findAll();

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment n : predefined) {
            appointmentDTOS.add(new AppointmentDTO(n));
        }

        return appointmentDTOS;
    }

    public boolean savePredefined(AppointmentDTO appointmentDTO) throws ParseException {

        if(appointmentRepository.findByName(appointmentDTO.getName()) != null)
            return false;

        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);

        Doctor doctor = doctorService.findOne(appointmentDTO.getDoctorEmail());
        ExamType examType = examTypeService.findOne(appointmentDTO.getExamTypeName());
        OR operationRoom = operationRoomService.findOne(appointmentDTO.getRoomNumber());

        appointment.setDoctor(doctor);
        appointment.setOr(operationRoom);
        appointment.setType(examType);

        String startDate=appointmentDTO.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = sdf1.parse(appointmentDTO.getDate());
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        appointment.setStart(finaldate);

        appointment.setClassification(AppointmentClassification.PREDEFINED);
        appointment.setStatus(AppointmentStatus.SHEDULED);

        appointmentRepository.save(appointment);

        return true;
    }

    public boolean IsCreated(String roomId, String examdate, String examtime, String endtime, AppointmentRequestDTO appointmentRequestDTO) {

        AppointmentRequest apreq = modelMapper.map(appointmentRequestService.findById(appointmentRequestDTO.getId()),AppointmentRequest.class);

        Doctor doctor = doctorService.findOne(appointmentRequestDTO.getDoctorEmail());

        Long id = Long.parseLong(roomId);

        OperationRoomDTO roomDTO = operationRoomService.findById(id);
        Appointment appointment = new Appointment();
        appointment.setStatus(AppointmentStatus.SHEDULED);
        appointment.setClassification(AppointmentClassification.NORMAL);

        if(roomDTO != null) {
            OR room = modelMapper.map(roomDTO, OR.class);

            appointment.setDoctor(doctor);

            Time t = Time.valueOf(examtime);
            appointment.setStartTime(t);

            Time endtimeTime = Time.valueOf(endtime);
            appointment.setEndTime(endtimeTime);

            appointment.setOr(room);

            appointment.setPatient(apreq.getPatient());

            Patient patient = patientService.findPatient(apreq.getPatient().getEmail());

            patient.getAppointments().add(appointment);
            doctor.getAppointments().add(appointment);
            room.getAppointments().add(appointment);

            appointmentRepository.save(appointment);

            patientService.updatePatient(patient);
            doctorService.updateDoctor(doctor);
            operationRoomService.update(room);

            return true;

        }

        return  false;

    }

    public Patient findByEmail(String email) {
        return null;
    }

    public Set<AppointmentDTO> getAllExams(){

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();
        Set<AppointmentDTO> returnAppointments = new HashSet<>();

        if(user.getRole() == Role.PATIENT) {
            Patient loggedinpatient = patientService.findPatient(user.getEmail());
            Set<Appointment> appointments = loggedinpatient.getAppointments();


            for (Appointment ap : appointments){
                if(ap.getStatus() ==  AppointmentStatus.SHEDULED && ap.getClassification() == AppointmentClassification.NORMAL){

                    AppointmentDTO appointmentDTO = modelMapper.map(ap,AppointmentDTO.class);
                    appointmentDTO.setDate(ap.getStart().toString().substring(0,10));
                    returnAppointments.add(appointmentDTO);

                }

            }
        }
        return returnAppointments;
    }


    public Set<AppointmentDTO> getAllExamsOld(){

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();
        Set<AppointmentDTO> returnAppointments = new HashSet<>();

        if(user.getRole() == Role.PATIENT) {
            Patient loggedinpatient = patientService.findPatient(user.getEmail());
            Set<Appointment> appointments = loggedinpatient.getAppointments();


            for (Appointment ap : appointments){
                if(ap.getStatus() ==  AppointmentStatus.HAS_HAPPEND && ap.getClassification() == AppointmentClassification.NORMAL){

                    AppointmentDTO appointmentDTO = modelMapper.map(ap,AppointmentDTO.class);
                    appointmentDTO.setDate(ap.getStart().toString().substring(0,10));
                    returnAppointments.add(appointmentDTO);

                }

            }
        }
        return returnAppointments;
    }

    public AppointmentDTO getOneAppoint(long id){
        Appointment app = appointmentRepository.findById(id);
        if(app.getStatus().equals(AppointmentStatus.SHEDULED)) {
            app.setStatus(AppointmentStatus.HAPPENING);
            appointmentRepository.save(app);
        }
        AppointmentDTO appDTO = modelMapper.map(app, AppointmentDTO.class);
        appDTO.setPatientemail(app.getPatient().getEmail());

        return appDTO;
    }

    public boolean endAppoint(long id){
        Appointment app = appointmentRepository.findById(id);

        if(app == null){
            return false;
        }
        if(app.getStatus().equals(AppointmentStatus.HAPPENING)) {
            app.setStatus(AppointmentStatus.HAS_HAPPEND);
            appointmentRepository.save(app);
        }
        return true;
    }

}

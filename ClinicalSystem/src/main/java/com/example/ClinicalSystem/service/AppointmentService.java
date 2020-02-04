package com.example.ClinicalSystem.service;

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


    public Set<OldExamDTO> getAllExamsOld(){

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();
        Set<OldExamDTO> returnExams = new HashSet<>();

        if(user.getRole() == Role.PATIENT) {
            Patient loggedinpatient = patientService.findPatient(user.getEmail());
            Set<Appointment> appointments = loggedinpatient.getAppointments();


            for (Appointment ap : appointments){
                if(ap.getStatus() ==  AppointmentStatus.HAS_HAPPEND && ap.getClassification() == AppointmentClassification.NORMAL){

                    OldExamDTO oldExamDTO = new OldExamDTO();
                    oldExamDTO.setDate(ap.getStart().toString().substring(0,10));

                    Doctor doctor = ap.getDoctor();
                    DoctorDTO doctorDTO = new DoctorDTO();
                    doctorDTO.setName(doctor.getName());
                    doctorDTO.setLastname(doctor.getLastname());
                    doctorDTO.setEmail(doctor.getEmail());

                    if(doctor.getSingleratings().size() == 0){
                        doctorDTO.setRating(0);
                    }else{

                        double suma=0;

                        for(Rating r : doctor.getSingleratings()){
                            suma = suma + r.getValue();
                        }
                        double rating = suma/(doctor.getSingleratings().size());
                        doctorDTO.setRating(rating);

                    }

                    for(Appointment appointment : doctor.getAppointments()){
                        if(appointment.getStatus().equals(AppointmentStatus.HAS_HAPPEND) && appointment.getClassification().equals(AppointmentClassification.NORMAL)){
                            doctorDTO.getPatients().add(appointment.getPatient().getEmail());
                        }
                    }


                    oldExamDTO.setDoctor(doctorDTO);
                    oldExamDTO.setRoomNumber(ap.getOr().getNumber());
                    oldExamDTO.setId(ap.getId());
                    oldExamDTO.setStartTime(ap.getStartTime());
                    oldExamDTO.setEndTime(ap.getEndTime());
                    oldExamDTO.setType(modelMapper.map(ap.getType(),ExamTypeDTO.class));

                    ClinicDTO clinicDTO =modelMapper.map(ap.getDoctor().getClinic(),ClinicDTO.class);

                    if(ap.getDoctor().getClinic().getSingleratings().size() == 0){
                        clinicDTO.setRating(0);
                    }else {

                        double suma = 0;

                        for (Rating r : ap.getDoctor().getClinic().getSingleratings()) {
                            suma = suma + r.getValue();
                        }
                        double rating = suma / (ap.getDoctor().getClinic().getSingleratings().size());
                        clinicDTO.setRating(rating);
                    }

                    List<String> patients = new ArrayList<>();
                    for(Doctor d : ap.getDoctor().getClinic().getDoctors()){

                        for(Appointment doctorap : d.getAppointments()){
                            if(doctorap.getStatus().equals(AppointmentStatus.HAS_HAPPEND) && doctorap.getClassification().equals(AppointmentClassification.NORMAL)){
                                patients.add(doctorap.getPatient().getEmail());
                            }
                        }

                    }

                    clinicDTO.setPatients(patients);

                    oldExamDTO.setClinic(clinicDTO);
                    returnExams.add(oldExamDTO);

                }

            }
        }
        return returnExams;
    }

}

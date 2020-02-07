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
    private EmailService emailService;

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


            Appointment saved = appointmentRepository.save(appointment);

            if (saved != null) {

                try {
                    emailService.sendEmailAboutAppointment(user,appointment);
                } catch (Exception e) {
                    return false;
                }

                return true;
            } else {
                return false;


            }
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

        //Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        Appointment appointment = new Appointment();

        Doctor doctor = doctorService.findOne(appointmentDTO.getDoctorEmail());
        ExamType examType = examTypeService.findOne(appointmentDTO.getExamTypeName());
        OR operationRoom = operationRoomService.findOne(appointmentDTO.getRoomNumber());

        appointment.setDoctor(doctor);
        appointment.setOr(operationRoom);
        appointment.setType(examType);
        appointment.setStartTime(appointmentDTO.getStartTime());
        appointment.setEndTime(appointmentDTO.getEndTime());
        appointment.setName(appointmentDTO.getName());

        String startDate=appointmentDTO.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(appointmentDTO.getDate());
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        appointment.setStart(finaldate);

        appointment.setClassification(AppointmentClassification.PREDEFINED);
        appointment.setStatus(AppointmentStatus.SHEDULED);
        appointmentRepository.save(appointment);

        doctor.getAppointments().add(appointment);
        operationRoom.getAppointments().add(appointment);


        doctorService.updateDoctor(doctor);
        operationRoomService.saveModel(operationRoom);


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

    public Set<UpcomingExamDTO> getAllExams(){

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();
        Set<UpcomingExamDTO> returnAppointments = new HashSet<>();

        if(user.getRole() == Role.PATIENT) {
            Patient loggedinpatient = patientService.findPatient(user.getEmail());
            Set<Appointment> appointments = loggedinpatient.getAppointments();


            for (Appointment ap : appointments){
                if(ap.getStatus() ==  AppointmentStatus.SHEDULED && ap.getClassification() == AppointmentClassification.NORMAL){

                    UpcomingExamDTO upcomingExamDTO = new UpcomingExamDTO();
                    upcomingExamDTO.setDate(ap.getStart().toString().substring(0,10));

                    Doctor doctor = ap.getDoctor();
                    DoctorDTO doctorDTO = new DoctorDTO();
                    doctorDTO.setName(doctor.getName());
                    doctorDTO.setLastname(doctor.getLastname());
                    doctorDTO.setEmail(doctor.getEmail());

                    upcomingExamDTO.setDoctor(doctorDTO);
                    upcomingExamDTO.setStartTime(ap.getStartTime());
                    upcomingExamDTO.setEndTime(ap.getEndTime());
                    upcomingExamDTO.setType(modelMapper.map(ap.getType(),ExamTypeDTO.class));
                    upcomingExamDTO.setRoomNumber(ap.getOr().getNumber());
                    upcomingExamDTO.setId(ap.getId());

                    returnAppointments.add(upcomingExamDTO);

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
                    oldExamDTO.setId(ap.getId());

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

    public DoctorDTO currentDoctor() {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        Doctor doctor = (Doctor) aut.getPrincipal();

        DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);

        List<AppointmentDTO> lista = new ArrayList<>();

        for(Appointment a : doctor.getAppointments()){
            AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
            appointmentDTO.setDate(a.getStart().toString().substring(0,10));
            appointmentDTO.setStartTime(a.getStartTime());
            appointmentDTO.setEndTime(a.getEndTime());
            lista.add(appointmentDTO);

        }

        doctorDTO.setAppointments(lista);

        return doctorDTO;
    }


    public boolean saveFromReqToAppointment(String id){

        AppointmentRequestDTO appointmentRequestDTO = appointmentRequestService.findById(Long.parseLong(id));
        Optional<AppointmentRequest> apreqop = appointmentRequestService.findByIdModel(appointmentRequestDTO.getId());

        AppointmentRequest apreq = apreqop.get();
        apreq.setAppointmentRequestStatus(AppointmentRequestStatus.CONFIRMED);
        appointmentRequestService.update(apreq);

        Appointment appointment = new Appointment();
        appointment.setName("Appointment");
        appointment.setStart(appointmentRequestDTO.getStart());
        appointment.setStartTime(appointmentRequestDTO.getStartTime());
        appointment.setEndTime(appointmentRequestDTO.getEndTime());
        appointment.setStatus(AppointmentStatus.SHEDULED);
        appointment.setClassification(AppointmentClassification.NORMAL);

        appointment.setType(doctorService.findOne(appointmentRequestDTO.getDoctorEmail()).getExamType());
        appointment.setOr(operationRoomService.findByIdModel(appointmentRequestDTO.getRoomId()));
        appointment.setDoctor(doctorService.findOne(appointmentRequestDTO.getDoctorEmail()));
        appointment.setPatient(patientService.findPatient(apreq.getPatient().getEmail()));


        Appointment saved = appointmentRepository.save(appointment);

        apreq.getPatient().getAppointments().add(appointment);
        doctorService.findOne(appointmentRequestDTO.getDoctorEmail()).getAppointments().add(appointment);
        operationRoomService.findByIdModel(appointmentRequestDTO.getRoomId()).getAppointments().add(appointment);

        //patientService.savePatient(apreq.getPatient());
        doctorService.updateDoctor(doctorService.findOne(appointmentRequestDTO.getDoctorEmail()));
        operationRoomService.saveModel(operationRoomService.findByIdModel(appointmentRequestDTO.getRoomId()));

        if(saved == null){
            return false;
        }else{
            return true;
        }

    }


    public boolean declineAppRequest(String id){

        Optional<AppointmentRequest> appointmentRequestop = appointmentRequestService.findByIdModel(Long.parseLong(id));

        AppointmentRequest appointmentRequest = appointmentRequestop.get();

        appointmentRequest.setAppointmentRequestStatus(AppointmentRequestStatus.DECLINED);

       return  appointmentRequestService.updateAppRequest(appointmentRequest);
    }

}

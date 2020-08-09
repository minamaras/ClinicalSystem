package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRequestRepository;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.Synchronize;
import org.joda.time.LocalDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


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

    @Autowired
    private OperationRoomService operationRoomService;


    public Optional<AppointmentRequest> findByIdModel(Long id){

        return appointmentRequestRepository.findById(id);
    }

    public void update(AppointmentRequest ap){

        appointmentRequestRepository.save(ap);
    }


    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public boolean saveAppointmentRequest(AppointmentRequestDTO appointmentRequestDTO) throws ParseException, UnsupportedEncodingException {

        //synchronized (this) {

            Authentication a = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) a.getPrincipal();
            Patient p = patientService.findPatient(user.getEmail());

       /* String startDate=appointmentRequestDTO.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = sdf1.parse(startDate);
        java.sql.Date finaldate = new java.sql.Date(date.getTime());*/

            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-mm-dd");
            java.time.LocalDate inputDates = java.time.LocalDate.parse(appointmentRequestDTO.getDate());
            java.sql.Date finaldate = java.sql.Date.valueOf(inputDates);

            for (AppointmentRequest r : p.getAppointmentRequests()) {

                if ((r.getDoctor().getId().equals(appointmentRequestDTO.getDoctorid())) && (r.getStartTime().equals(appointmentRequestDTO.getStartTime()))
                        && (r.getStart().equals(finaldate)) && (r.getType().getName().equals(appointmentRequestDTO.getExamTypeName())) && r.getAppointmentRequestStatus().equals(AppointmentRequestStatus.PATIENTSENT)) {

                    return false;
                }
            }

            try {
                List<AppointmentRequest> reqs = appointmentRequestRepository.findAll();

                for (AppointmentRequest ap : reqs) {

                    if (ap.getAppointmentRequestStatus().equals(AppointmentRequestStatus.PATIENTSENT)) {


                        if ((ap.getStart().equals(finaldate)) && (ap.getStartTime().equals(appointmentRequestDTO.getStartTime())) &&
                                (ap.getDoctor().getId().equals(appointmentRequestDTO.getDoctorid()))) {

                            return false;
                        }

                    }

                }
            } catch (Exception e){
                return false;
            }

            AppointmentRequest appointmentRequest = modelMapper.map(appointmentRequestDTO, AppointmentRequest.class);
            appointmentRequest.setPatient(p);
            appointmentRequest.setAppointmentRequestStatus(AppointmentRequestStatus.PATIENTSENT);
            appointmentRequest.setStart(finaldate);

            String decoded = URLDecoder.decode(appointmentRequestDTO.getExamTypeName(), "UTF-8");
            ExamType examType = examTypeService.findOne(decoded);
            if (examType != null) {
                appointmentRequest.setType(examType);
                java.sql.Time startTime = java.sql.Time.valueOf(appointmentRequest.getStartTime().toString());
                LocalTime localtime = startTime.toLocalTime();
                localtime = localtime.plusMinutes(examType.getDuration());
                Time endTime = Time.valueOf(localtime);
                appointmentRequest.setEndTime(endTime);
            }

            Doctor doctor = doctorService.findDoctorById(appointmentRequestDTO.getDoctorid());
            p.getAppointmentRequests().add(appointmentRequest);

            if (doctor != null) {
                appointmentRequest.setDoctor(doctor);
                appointmentRequestDTO.setDoctorEmail(doctor.getEmail());
            }

            Clinic clinic = doctor.getClinic();
            List<ClinicAdmin> admins = new ArrayList<>();
            for (ClinicAdmin ca : clinic.getClinicAdmins()) {
                admins.add(ca);
            }

            int randomNumberAdmin = (int) (Math.random() * admins.size());


            //provera da li postoji vec taj u bazi

            //List<AppointmentRequest> reqpatient = appointmentRequestRepository.findByPatient(p);


            //AppointmentRequest reqdate = appointmentRequestRepository.findByStart((Date) appointmentRequest.getStart());
            //AppointmentRequest reqtime = appointmentRequestRepository.findByStartTime(appointmentRequest.getStartTime());
            //AppointmentRequest reqtype = appointmentRequestRepository.findByType(appointmentRequest.getType());

            if (appointmentRequestRepository.save(appointmentRequest) != null) {

                try {
                    emailService.sendAdminNotificaitionAsync(admins.get(randomNumberAdmin), p, finaldate, appointmentRequest.getStartTime(), appointmentRequest.getEndTime(), appointmentRequest.getType());
                } catch (Exception e) {
                    return false;
                }

                return true;
            } else {
                return false;


            }


        }

    //}


    public AppointmentRequest saveApReq(AppointmentRequest apreq){
        return appointmentRequestRepository.save(apreq);
    }



    public List<AppointmentRequestDTO> findAll() throws ParseException {
        List<AppointmentRequest> requests = appointmentRequestRepository.findAllByOrderByStartAsc();

        List<AppointmentRequestDTO> appointmentRequestDTOS = new ArrayList<>();
        for (AppointmentRequest c : requests) {
            if(c.getAppointmentRequestStatus().equals(AppointmentRequestStatus.PATIENTSENT) || c.getAppointmentRequestStatus().equals(AppointmentRequestStatus.DOCTORSENT)){

                AppointmentRequestDTO appointmentRequestDTO =modelMapper.map(c,AppointmentRequestDTO.class);
                appointmentRequestDTO.setStart(c.getStart());
                appointmentRequestDTO.setStartTime(c.getStartTime());
                appointmentRequestDTO.setEndTime(c.getEndTime());

                appointmentRequestDTOS.add(appointmentRequestDTO);
            }
        }

        return appointmentRequestDTOS;
    }



    public List<AppointmentRequestDTO> findAllWaiting() throws ParseException {

        List<AppointmentRequest> requests = appointmentRequestRepository.findAllByOrderByStartAsc();

        List<AppointmentRequestDTO> appointmentRequestDTOS = new ArrayList<>();
        for (AppointmentRequest c : requests) {

            if (c.getAppointmentRequestStatus().equals(AppointmentRequestStatus.WAITING)) {

                AppointmentRequestDTO appointmentRequestDTO = modelMapper.map(c, AppointmentRequestDTO.class);
                appointmentRequestDTO.setStart(c.getStart());
                appointmentRequestDTO.setStartTime(c.getStartTime());
                appointmentRequestDTO.setEndTime(c.getEndTime());

                appointmentRequestDTOS.add(appointmentRequestDTO);
            }

        }


        return appointmentRequestDTOS;
    }

    public AppointmentRequest findByName(String name) {
        return appointmentRequestRepository.findByName(name);
    }

    public List<AppointmentRequestDTO> findAllSent() throws ParseException {

        List<AppointmentRequest> requests = appointmentRequestRepository.findAll();

        List<AppointmentRequestDTO> appointmentRequestDTOS = new ArrayList<>();
        for (AppointmentRequest c : requests) {

            if (c.getAppointmentRequestStatus().equals(AppointmentRequestStatus.PATIENTSENT) || c.getAppointmentRequestStatus().equals(AppointmentRequestStatus.DOCTORSENT)) {

                AppointmentRequestDTO appointmentRequestDTO = modelMapper.map(c, AppointmentRequestDTO.class);
                appointmentRequestDTO.setStart(c.getStart());
                appointmentRequestDTO.setStartTime(c.getStartTime());
                appointmentRequestDTO.setEndTime(c.getEndTime());

                appointmentRequestDTOS.add(appointmentRequestDTO);
            }

        }


        return appointmentRequestDTOS;
    }

    public AppointmentRequestDTO findById(Long id){
        Optional<AppointmentRequest> ap =appointmentRequestRepository.findById(id);
        return modelMapper.map(ap.get(),AppointmentRequestDTO.class);
    }

    public Optional<AppointmentRequest> findByIdModelReq(Long id) {
        return appointmentRequestRepository.findById(id);
    }

    public AppointmentRequest findByIdMina(long id) {
        Optional<AppointmentRequest> ap = appointmentRequestRepository.findById(id);
        return modelMapper.map(ap.get(), AppointmentRequest.class);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean IsCreated(String roomId, String examdate, String examtime, String endtime, AppointmentRequestDTO appointmentRequestDTO) throws ParseException {

        Optional<AppointmentRequest> appointmentRequest = appointmentRequestRepository.findById(appointmentRequestDTO.getId());

            AppointmentRequest apreq = appointmentRequest.get();
            apreq.setPatient(appointmentRequest.get().getPatient());


        //Doctor doctor = appointmentRequest.get().getDoctor();

        Long id = Long.parseLong(roomId);

        OR roomDTO = operationRoomService.findByIdModelMina(id);
        if(roomDTO == null) {
            return  false;
        }


        if(roomDTO != null) {

            //apreq.setDoctor(doctor);

            Time t = Time.valueOf(examtime);
            apreq.setStartTime(t);

            Time endtimeTime = Time.valueOf(endtime);
            apreq.setEndTime(endtimeTime);

            Date date = Date.valueOf(examdate);
            apreq.setStart(date);

            apreq.setRoomId(id);
            apreq.setAppointmentRequestStatus(AppointmentRequestStatus.WAITING);

            LocalDate requestDate = LocalDate.fromDateFields(date);

            if(appointmentRequest.get().getDoctor().getAppointments().isEmpty()) {

                apreq.setDoctor(appointmentRequest.get().getDoctor());

            } else {
                List<Doctor> doctors = doctorService.findAllDoctors();

                List<Doctor> typeDoctors = new ArrayList<>();

                for(Doctor d : doctors) {
                    if(d.getExamType().getName().equals(apreq.getType().getName())) {

                       typeDoctors.add(d);

                    }
                }

                for(Doctor d : typeDoctors) {

                        for(Appointment a : d.getAppointments()) {

                            org.joda.time.LocalTime requestStartTime = org.joda.time.LocalTime.fromDateFields(t);
                            org.joda.time.LocalTime requestEndTime = org.joda.time.LocalTime.fromDateFields(endtimeTime);

                            org.joda.time.LocalTime appointmentStartTime = org.joda.time.LocalTime.fromDateFields(a.getStartTime());
                            org.joda.time.LocalTime appointmentEndTime = org.joda.time.LocalTime.fromDateFields(a.getEndTime());

                            LocalDate appointmentDate = LocalDate.fromDateFields(a.getStart());

                            if(appointmentDate.isEqual(requestDate)) {
                                if(appointmentStartTime.isEqual(requestStartTime)) {

                                    typeDoctors.remove(d);

                                } else if(appointmentStartTime.isBefore(requestStartTime) && appointmentEndTime.isBefore(requestEndTime)) {

                                    continue;

                                } else if(appointmentStartTime.isAfter(requestStartTime) && appointmentEndTime.isAfter(requestEndTime)) {

                                    continue;

                                } else if(appointmentStartTime.isBefore(requestStartTime) && appointmentStartTime.isBefore(requestEndTime)) {

                                    typeDoctors.remove(d);

                                }
                            }

                        }

                }

                int randomDoctor = (int)(Math.random() * typeDoctors.size());
                apreq.setDoctor(typeDoctors.get(randomDoctor));
            }

            appointmentRequest.get().setAppointmentRequestStatus(AppointmentRequestStatus.WAITING);
            apreq = saveApReq(apreq);
            if(apreq != null)
              {
                  sendRequest(roomId,examdate,examtime,endtime,apreq.getId(),appointmentRequestDTO);
                  findAllSent();
                  return true;
              }

            return false;

        }

        return  false;

    }


    public boolean sendRequest(String roomId, String examdate, String examtime, String endtime, Long id,AppointmentRequestDTO apDTO) {


        //Long requestId = Long.parseLong(id);
        Optional<AppointmentRequest> appointmentRequest = appointmentRequestRepository.findById(id);
        Patient patient = appointmentRequest.get().getPatient();

        if(patient != null) {
            try {
                emailService.sendAppointmentRequest(patient, examdate, examtime, endtime,apDTO);
            } catch (Exception e) {
                return false;
            }


            return true;
        }

        return false;

    }


    public boolean updateAppRequest(AppointmentRequest appointmentRequest){
      AppointmentRequest ap =  appointmentRequestRepository.save(appointmentRequest);

      if( ap !=  null){
          return true;
      }else{
          return  false;
      }
    }

    public List<AppointmentRequestDTO> findMyExams() {

        List<AppointmentRequestDTO> appointmentRequestDTOS = new ArrayList<AppointmentRequestDTO>();

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole() == Role.PATIENT) {

            Patient p = patientService.findPatient(user.getEmail());

            Set<AppointmentRequest> appointmentRequests = p.getAppointmentRequests();

            for (AppointmentRequest apreq : appointmentRequests) {

                if (apreq.getAppointmentRequestStatus().equals(AppointmentRequestStatus.WAITING)) {
                    AppointmentRequestDTO appointmentRequestDTO = modelMapper.map(apreq, AppointmentRequestDTO.class);
                    appointmentRequestDTO.setExamTypeName(apreq.getType().getName());
                    appointmentRequestDTO.setDoctorEmail(apreq.getDoctor().getEmail());
                    appointmentRequestDTO.setRoomId(apreq.getRoomId());
                    appointmentRequestDTO.setName(apreq.getName());
                    appointmentRequestDTOS.add(appointmentRequestDTO);
                }

            }
        }
        return appointmentRequestDTOS;
    }

    public boolean scheduleAnother(int doctorId, String examDate, String patientEmail, String startExam, String endExam) {

        Doctor doctor = doctorService.findDoctorById(Long.valueOf(doctorId));
        Patient patient = patientService.findPatient(patientEmail);

        AppointmentRequest appointmentRequest = new AppointmentRequest();

        Time t = Time.valueOf(startExam);
        appointmentRequest.setStartTime(t);

        Time endtimeTime = Time.valueOf(endExam);
        appointmentRequest.setEndTime(endtimeTime);

        Date date = Date.valueOf(examDate);
        appointmentRequest.setStart(date);

        appointmentRequest.setDoctor(doctor);

        appointmentRequest.setPatient(patient);

        appointmentRequest.setType(doctor.getExamType());

        appointmentRequest.setAppointmentRequestStatus(AppointmentRequestStatus.DOCTORSENT);

        appointmentRequestRepository.save(appointmentRequest);

        //sendDoctorRequest(doctor,patient,examDate,startExam, endExam, appointmentRequest.getId());
        return true;

    }

    public boolean sendDoctorRequest(Doctor doctor, Patient patient, String examdate, String examtime, String endtime, Long idRequest) {

        Optional<AppointmentRequest> appointmentRequest = appointmentRequestRepository.findById(idRequest);

        if(doctor != null) {
            try {
                emailService.sendDoctorRequest(doctor, patient, examdate, examtime, endtime, idRequest);
            } catch (Exception e) {
                return false;
            }

            appointmentRequest.get().setAppointmentRequestStatus(AppointmentRequestStatus.PATIENTSENT);
            appointmentRequestRepository.save(appointmentRequest.get());
            return true;
        }

        return false;

    }

    public boolean assignRoomToRequest(String roomId, String examdate, String examtime, String endtime, AppointmentRequestDTO appointmentRequestDTO) throws ParseException {

        AppointmentRequest apreq = findByIdMina(appointmentRequestDTO.getId());

        Optional<AppointmentRequest> appointmentRequest = appointmentRequestRepository.findById(appointmentRequestDTO.getId());

        if(appointmentRequest.isPresent()) {
            apreq.setPatient(appointmentRequest.get().getPatient());
        }

        //Doctor doctor = appointmentRequest.get().getDoctor();

        Long id = Long.parseLong(roomId);

        OR roomDTO = operationRoomService.findByIdModelMina(id);
        if(roomDTO == null) {
            return  false;
        }


        if(roomDTO != null) {

            //apreq.setDoctor(doctor);

            Time t = Time.valueOf(examtime);
            apreq.setStartTime(t);

            Time endtimeTime = Time.valueOf(endtime);
            apreq.setEndTime(endtimeTime);

            Date date = Date.valueOf(examdate);
            apreq.setStart(date);

            apreq.setRoomId(id);
            apreq.setAppointmentRequestStatus(AppointmentRequestStatus.WAITING);

            LocalDate requestDate = LocalDate.fromDateFields(date);

            if(appointmentRequest.get().getDoctor().getAppointments().isEmpty()) {

                apreq.setDoctor(appointmentRequest.get().getDoctor());

            } else {
                List<Doctor> doctors = doctorService.findAllDoctors();

                List<Doctor> typeDoctors = new ArrayList<>();

                for(Doctor d : doctors) {
                    if(d.getExamType().getName().equals(apreq.getType().getName())) {

                        typeDoctors.add(d);

                    }
                }

                for(Doctor d : typeDoctors) {

                    for(Appointment a : d.getAppointments()) {

                        org.joda.time.LocalTime requestStartTime = org.joda.time.LocalTime.fromDateFields(t);
                        org.joda.time.LocalTime requestEndTime = org.joda.time.LocalTime.fromDateFields(endtimeTime);

                        org.joda.time.LocalTime appointmentStartTime = org.joda.time.LocalTime.fromDateFields(a.getStartTime());
                        org.joda.time.LocalTime appointmentEndTime = org.joda.time.LocalTime.fromDateFields(a.getEndTime());

                        LocalDate appointmentDate = LocalDate.fromDateFields(a.getStart());

                        if(appointmentDate.isEqual(requestDate)) {
                            if(appointmentStartTime.isEqual(requestStartTime)) {

                                typeDoctors.remove(d);

                            } else if(appointmentStartTime.isBefore(requestStartTime) && appointmentEndTime.isBefore(requestEndTime)) {

                                continue;

                            } else if(appointmentStartTime.isAfter(requestStartTime) && appointmentEndTime.isAfter(requestEndTime)) {

                                continue;

                            } else if(appointmentStartTime.isBefore(requestStartTime) && appointmentStartTime.isBefore(requestEndTime)) {

                                typeDoctors.remove(d);

                            }
                        }

                    }

                }

                int randomDoctor = (int)(Math.random() * typeDoctors.size());
                apreq.setDoctor(typeDoctors.get(randomDoctor));
            }

            apreq = appointmentRequestRepository.save(apreq);
            if(apreq != null)
            {
                return true;
            }

            return false;

        }

        return  false;

    }
}




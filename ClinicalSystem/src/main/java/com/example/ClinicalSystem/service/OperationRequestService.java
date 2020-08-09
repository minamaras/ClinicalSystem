package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OperationParamsDTO;
import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.OperationRequestRepository;
//import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.util.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OperationRequestService {


    @Autowired
    private OperationRequestRepository operationRequestRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private OperationRoomService operationRoomService;

    @Autowired
    private EmailService emailService;

    public OperationRequest findOne(long id){
        return operationRequestRepository.findById(id);
    }

    public List<OperationRequestDTO> allRequests() {
        List<OperationRequest> operationRequests = operationRequestRepository.findAll();
        List<OperationRequestDTO> operationRequestDTOS = new ArrayList<>();

        for (OperationRequest or : operationRequests) {
            if (!or.isScheduled()) {
                OperationRequestDTO orDTO = new OperationRequestDTO(or);
                orDTO.setStart(or.getStart().toString().substring(0, 10));
                orDTO.setStartTime(or.getStartTime());
                orDTO.setEndTime(or.getEndTime());
                operationRequestDTOS.add(orDTO);
            }
        }
        return operationRequestDTOS;
    }


    public OperationRequestDTO getOne(long id) {
        OperationRequest or = operationRequestRepository.findById(id);

        OperationRequestDTO oprDTO = new OperationRequestDTO(or);
        oprDTO.setStart(or.getStart().toString().substring(0, 10));
        oprDTO.setStartTime(or.getStartTime());
        oprDTO.setEndTime(or.getEndTime());

        return oprDTO;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean scheduleOperation(int doctorId, String examDate, String patientEmail, String startExam, String endExam, OperationRequestDTO operationRequestDTO) {
        Doctor doctor = doctorService.findDoctorById(Long.valueOf(doctorId));
        Patient patient = patientService.findPatient(patientEmail);

        OperationRequest operationRequest = new OperationRequest();

        Time t = Time.valueOf(startExam);
        operationRequest.setStartTime(t);

        Time endtimeTime = Time.valueOf(endExam);
        operationRequest.setEndTime(endtimeTime);

        Date date = Date.valueOf(examDate);
        operationRequest.setStart(date);

        operationRequest.setPatient(patient);

        operationRequest.setType(doctor.getExamType());

        operationRequest.setName(operationRequestDTO.getName());

        if(operationRequestRepository.save(operationRequest) != null) {
            return true;
        }

        return false;
    }

    @Transactional(readOnly = false)
    public OperationRequest save(OperationRequest operationRequest){
        return operationRequestRepository.save(operationRequest);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public OperationRequest update(OperationRequest operationRequest){
       return operationRequestRepository.save(operationRequest);
    }



    public boolean scheduleOperation(OperationRequestDTO operationRequestDTO) throws InterruptedException {
        OperationRequest operation = operationRequestRepository.findById(operationRequestDTO.getId());

        if (operation == null) {
            return false;
        }

        if (operation.isScheduled()) {
            return false;
        }

        java.util.Date originalDate = operation.getStart();

        String inputDateString;
        if (operationRequestDTO.getStart() == null) {
            inputDateString = operationRequestDTO.getDate();
        } else {
            inputDateString = operationRequestDTO.getStart();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate inputDate = LocalDate.parse(inputDateString);
        java.sql.Date finalDate = java.sql.Date.valueOf(inputDate);


        LocalTime startTime = operationRequestDTO.getStartTime().toLocalTime();
        LocalTime endTime = startTime.plusHours(2);
        Time finalStartTime = Time.valueOf(startTime);
        Time finalEndTime = Time.valueOf(endTime);

        String thisD = finalDate.toString().substring(0, 10);
        String starts = finalStartTime.toString();


        List<OperationRequest> operationRequests = operationRequestRepository.findAll();
        for(OperationRequest operationRequest : operationRequests) {
            if (operationRequest.isScheduled()) {


                if (operationRequest.getOr().getNumber() == operationRequestDTO.getRoomNumber()) {

                    if ((operationRequest.getStart().compareTo(finalDate) == 0) && (operationRequest.getStartTime().compareTo(finalStartTime) == 0)) {
                        return false;
                    }
                }


                for (String doctorName : operationRequestDTO.getDoctorNames()) {
                    Doctor doctor = doctorService.findOne(doctorName);
                    if ((operationRequest.getStart().compareTo(finalDate) == 0) && operationRequest.getStartTime().compareTo(finalStartTime) == 0) {

                        if (!operationRequest.getDoctors().isEmpty()) {

                            if (operationRequest.getDoctors().contains(doctor)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }



        operation.setStart(finalDate);
        operation.setStartTime(finalStartTime);
        operation.setEndTime(finalEndTime);


        OR oroom = operationRoomService.findOne(operationRequestDTO.getRoomNumber());
        operation.setOr(oroom);


        oroom.getOperations().add(operation);


        for (String name : operationRequestDTO.getDoctorNames()) {
            Doctor doctor = doctorService.findOne(name);

            operation.getDoctors().add(doctor);
            emailService.sendOperationInfoDoctor(doctor, thisD, starts);
        }

        operation.setScheduled(true);
        update(operation);



        operationRoomService.save(oroom);

        Patient patient = operation.getPatient();

        if (originalDate.compareTo(finalDate) > 0 || originalDate.compareTo(finalDate) < 0) {

            emailService.sendOperationDateChange(patient, thisD, starts);
        } else {

            emailService.sendOperationInfo(patient, thisD, starts);
        }


        return true;

    }



    public void assignOperationAutomatically() throws ParseException, InterruptedException {
        List<OperationRequest> operations = operationRequestRepository.findAll();

        List<OperationRequest> notChecked = new ArrayList<>();
        for (OperationRequest op : operations){
            if(!op.isScheduled()){
                notChecked.add(op);
            }
        }

        if(!notChecked.isEmpty()) {

            for (OperationRequest operation : notChecked) {

                if (operation.isScheduled()) {
                    return;
                }

                Random random = new Random();
                //int minDay = (int) new Date().getTime();
                int minDay = (int) LocalDate.of(2020, 1, 1).toEpochDay();
                int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
                long randomDay = minDay + random.nextInt(maxDay - minDay);

                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
                java.util.Date finalDate = java.sql.Date.valueOf(randomDate);
                String thisD = finalDate.toString().substring(0, 10);


                OperationRequestDTO operationRequestDTO = new OperationRequestDTO();
                operationRequestDTO.setId(operation.getId());
                operationRequestDTO.setDate(thisD);
                operationRequestDTO.setStartTime(operation.getStartTime());
                operationRequestDTO.setEndTime(operation.getEndTime());

                List<OperationRoomDTO> operationRoomDTOS = operationRoomService.allRoomsForOperationRandom(finalDate, operation.getStartTime(), operation.getEndTime());

                int randomRoom = (int) (Math.random() * operationRoomDTOS.size());
                int roomNum = operationRoomDTOS.get(randomRoom).getNumber();
                OperationParamsDTO operationParamsDTO = new OperationParamsDTO(operation.getId(), thisD, operation.getStartTime().toString(), roomNum);

                List<DoctorDTO> freeDoctors = doctorService.getFreeDoctorsForOperation(operationParamsDTO);

                operationRequestDTO.setRoomNumber(roomNum);

                List<String> docNames = new ArrayList<>();
                for (DoctorDTO doc : freeDoctors) {
                    docNames.add(doc.getEmail());
                }
                operationRequestDTO.setDoctorNames(docNames);

                scheduleOperation(operationRequestDTO);


            }
        }

    }

}


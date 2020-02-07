package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.OperationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OperationRequestService {


    @Autowired
    private OperationRequestRepository operationRequestRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private OperationRoomService operationRoomService;

    @Autowired
    private EmailService emailService;

    public OperationRequest findOne(long id){
        return operationRequestRepository.findById(id);
    }

    public List<OperationRequestDTO> allRequests(){
        List<OperationRequest> operationRequests = operationRequestRepository.findAll();
        List<OperationRequestDTO> operationRequestDTOS = new ArrayList<>();

        for(OperationRequest or : operationRequests){
            if(!or.isScheduled()) {
                OperationRequestDTO orDTO = new OperationRequestDTO(or);
                orDTO.setStart(or.getStart().toString().substring(0,10));
                orDTO.setStartTime(or.getStartTime());
                orDTO.setEndTime(or.getEndTime());
                operationRequestDTOS.add(orDTO);
            }
        }
        return operationRequestDTOS;
    }

    public OperationRequestDTO getOne(long id){
        OperationRequest or = operationRequestRepository.findById(id);

        OperationRequestDTO oprDTO = new OperationRequestDTO(or);
        oprDTO.setStart(or.getStart().toString().substring(0,10));
        oprDTO.setStartTime(or.getStartTime());
        oprDTO.setEndTime(or.getEndTime());

        return oprDTO;
    }

    public OperationRequest save(OperationRequest operationRequest){
       return operationRequestRepository.save(operationRequest);
    }


    public boolean scheduleOperation(OperationRequestDTO operationRequestDTO) throws InterruptedException {

        OperationRequest operation = operationRequestRepository.findById(operationRequestDTO.getId());

        if(operation == null){
            return false;
        }

        java.util.Date originalDate = operation.getStart();

        String inputDateString = operationRequestDTO.getStart();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate inputDate = LocalDate.parse(inputDateString);
        java.sql.Date finalDate = java.sql.Date.valueOf(inputDate);

        LocalTime startTime = operationRequestDTO.getStartTime().toLocalTime();
        LocalTime endTime = startTime.plusHours(2);
        Time finalStartTime = Time.valueOf(startTime);
        Time finalEndTime = Time.valueOf(endTime);

        String thisD = finalDate.toString().substring(0,10);
        String starts = finalStartTime.toString();

        operation.setStart(finalDate);
        operation.setStartTime(finalStartTime);
        operation.setEndTime(finalEndTime);



        OR oroom = operationRoomService.findOne(operationRequestDTO.getRoomNumber());
        operation.setOr(oroom);
        //save(operation);

        oroom.getOperations().add(operation);



        for(String name : operationRequestDTO.getDoctorNames()){
            Doctor doctor = doctorService.findOne(name);
            operation.getDoctors().add(doctor);
            emailService.sendOperationInfoDoctor(doctor,thisD,starts);
            //doctor.getOperations().add(operation);
        }

        operation.setScheduled(true);
        save(operation);
        operationRoomService.save(oroom);

        Patient patient = operation.getPatient();

        if(originalDate.compareTo(finalDate) > 0 ||  originalDate.compareTo(finalDate) < 0){

            emailService.sendOperationDateChange(patient,thisD,starts);
        } else {

            emailService.sendOperationInfo(patient,thisD,starts);
        }



        return true;

    }

}

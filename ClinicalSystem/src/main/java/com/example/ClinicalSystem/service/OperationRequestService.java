package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.OperationRequestRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
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

    public OperationRequest findOne(long id) {
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


    public boolean scheduleOperation(int doctorId, String examDate, String patientEmail, String startExam, String endExam) {
        Doctor doctor = doctorService.findOneById(Long.valueOf(doctorId));
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

        if(operationRequestRepository.save(operationRequest) != null) {
            return true;
        }

        return false;
    }
}


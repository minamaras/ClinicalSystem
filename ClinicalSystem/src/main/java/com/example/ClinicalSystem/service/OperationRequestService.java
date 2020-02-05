package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.model.OperationRequest;
import com.example.ClinicalSystem.repository.OperationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperationRequestService {

    @Autowired
    private OperationRequestRepository operationRequestRepository;

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

}

package com.example.ClinicalSystem.service;

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
            OperationRequestDTO orDTO = new OperationRequestDTO(or);
            operationRequestDTOS.add(orDTO);
        }
        return operationRequestDTOS;
    }

}

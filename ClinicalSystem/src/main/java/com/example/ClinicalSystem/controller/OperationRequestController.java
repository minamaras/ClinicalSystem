package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.OperationRequest;
import com.example.ClinicalSystem.service.OperationRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/operationrequests")
public class OperationRequestController {

    @Autowired
    private OperationRequestService operationRequestService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/allrequests")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<List<OperationRequestDTO>> getAllRequests() {

        List<OperationRequestDTO> requestsDTO = operationRequestService.allRequests();

        return new ResponseEntity<>(requestsDTO, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/one/{id}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<OperationRequestDTO> getOneRequest(@PathVariable long id) {

        OperationRequestDTO requestDTO = operationRequestService.getOne(id);

        return new ResponseEntity<>(requestDTO, HttpStatus.OK);
    }
}

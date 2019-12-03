package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.service.OperationRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/rooms")
public class OperationRoomController {

    @Autowired
    private OperationRoomService roomService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<OperationRoomDTO> addRoom(@RequestBody OperationRoomDTO roomDTO) {

        roomService.save(roomDTO);
        return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);

    }
}
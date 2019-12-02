package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.CreateOperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.service.ORService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/ors")
public class ORController {

    @Autowired
    ORService orService;
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<List<CreateOperationRoomDTO>> getAllRooms() {

        List<CreateOperationRoomDTO> rooms = orService.findAllRooms();

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}

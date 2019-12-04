package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.service.OperationRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/rooms")
public class OperationRoomController {

    @Autowired
    private OperationRoomService roomService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<List<OperationRoomDTO>> getAllRooms() {

        List<OperationRoomDTO> rooms = roomService.findAll();

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<OperationRoomDTO> addRoom(@RequestBody OperationRoomDTO roomDTO) {

        if(roomService.save(roomDTO))
            return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/deleteroom")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<Void> deleteRoom(@RequestBody OperationRoomDTO roomDto) {

        if(roomService.removeRoom(roomDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
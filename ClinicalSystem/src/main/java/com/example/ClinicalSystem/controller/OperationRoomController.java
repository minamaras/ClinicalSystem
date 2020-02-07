package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.DoctorDTO;
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
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Set;


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
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<OperationRoomDTO> addRoom(@RequestBody OperationRoomDTO roomDTO, Principal p) {

        if(roomService.save(roomDTO, p))
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

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<OperationRoomDTO> update(@RequestBody OperationRoomDTO roomDto) {

        if(roomService.findOne(roomDto.getNumber()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        OR room = roomService.findOne(roomDto.getNumber());

        if(roomDto.getName() != "")
            room.setName(roomDto.getName());

        roomService.update(room);

        return new ResponseEntity<>(modelMapper.map(room, OperationRoomDTO.class), HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/clinicsrooms/{clinicname}")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<Set<OperationRoomDTO>>RoomsOfClinic(@PathVariable String clinicname) throws ParseException {


        Set<OperationRoomDTO> roomDTOS = roomService.findAllRoomsFromAClinic(clinicname);
        return new ResponseEntity<>(roomDTOS, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/clinicsoperationrooms")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<Set<OperationRoomDTO>> roomsForOperation(Principal p) throws ParseException {


        Set<OperationRoomDTO> roomDTOS = roomService.allRoomsForOperationFromAClinic(p);
        return new ResponseEntity<>(roomDTOS, HttpStatus.OK);

    }


}
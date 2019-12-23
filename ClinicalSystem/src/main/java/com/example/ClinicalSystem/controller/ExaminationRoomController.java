package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ExaminationRoomDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.ExaminationRoom;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.service.ExaminationRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/examinationrooms")
public class ExaminationRoomController {

    @Autowired
    private ExaminationRoomService examinationRoomService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<List<ExaminationRoomDTO>> getAllRooms() {

        List<ExaminationRoomDTO> rooms = examinationRoomService.findAll();

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<ExaminationRoomDTO> addRoom(@RequestBody ExaminationRoomDTO roomDTO) {

        if(examinationRoomService.save(roomDTO))
            return new ResponseEntity<>(roomDTO, HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/deleteroom")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<Void> deleteRoom(@RequestBody ExaminationRoomDTO roomDto) {

        if(examinationRoomService.removeRoom(roomDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<ExaminationRoomDTO> update(@RequestBody ExaminationRoomDTO roomDto) {

        if(examinationRoomService.findOne(roomDto.getNumber()) == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        ExaminationRoom room = examinationRoomService.findOne(roomDto.getNumber());

        if(roomDto.getName() != "")
            room.setName(roomDto.getName());

        examinationRoomService.update(room);

        return new ResponseEntity<>(modelMapper.map(room, ExaminationRoomDTO.class), HttpStatus.OK);

    }


}

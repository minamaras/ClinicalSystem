package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.repository.OperationRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class OperationRoomService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OperationRoomRepository repo;

    public List<OperationRoomDTO> findAll() {

        List<OR> rooms = repo.findAll();

        List<OperationRoomDTO> roomsDTO = new ArrayList<>();
        for (OR r : rooms) {
            roomsDTO.add(modelMapper.map(r, OperationRoomDTO.class));
        }

        return roomsDTO;
    }

    public boolean save(OperationRoomDTO roomDto) {

        OR room = modelMapper.map(roomDto, OR.class);
        if(repo.findByNumber(roomDto.getNumber()) != null) {

            return false;
        }
        roomDto.setExamTypeName(room.getExamType().getName());
        repo.save(room);
        return true;
    }

    @Transactional
    public boolean removeRoom(OperationRoomDTO roomDTO) {

        if(repo.findByNumber(roomDTO.getNumber()) != null) {

            OR room = modelMapper.map(roomDTO, OR.class);

            if(room.getAppointments().isEmpty()) {
                repo.deleteByNumber(room.getNumber());
                return true;
            }
        }

        return false;

    }

    public OR findOne(int number) {
        return repo.findByNumber(number);
    }

    public OR update(OR or) {
        return repo.save(or);
    }

}

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
            roomsDTO.add(new OperationRoomDTO(r));
        }

        return roomsDTO;
    }

    public boolean save(OperationRoomDTO roomDto) {

        OR room = modelMapper.map(roomDto, OR.class);
        if(repo.findByNumber(roomDto.getNumber()) != null) {

            return false;
        }

        if(roomDto.getReserved() == "Yes" || roomDto.getReserved() == "yes") {
            roomDto.setReserved(true);
        }

        roomDto.setReserved(false);

        repo.save(room);
        return true;
    }

    @Transactional
    public boolean removeRoom(OperationRoomDTO roomDTO) {

        if(repo.findByNumber(roomDTO.getNumber()) != null) {

            if(roomDTO.isReserved()) {

                return false;
            }

            OR room = modelMapper.map(roomDTO, OR.class);

            repo.deleteByNumber(room.getNumber());

            return true;
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

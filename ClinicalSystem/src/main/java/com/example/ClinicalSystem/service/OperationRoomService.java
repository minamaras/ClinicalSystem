package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.repository.OperationRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OperationRoomService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OperationRoomRepository repo;

    public OR save(OperationRoomDTO roomDto) {

        OR room = modelMapper.map(roomDto, OR.class);

        return repo.save(room);

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

}

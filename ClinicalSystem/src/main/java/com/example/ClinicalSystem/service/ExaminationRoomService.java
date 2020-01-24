package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ExaminationRoomDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.ExaminationRoom;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.repository.ExaminationRoomRepository;
import com.example.ClinicalSystem.repository.OperationRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExaminationRoomService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ExaminationRoomRepository repo;

    public List<ExaminationRoomDTO> findAll() {

        List<ExaminationRoom> rooms = repo.findAll();

        List<ExaminationRoomDTO> roomsDTO = new ArrayList<>();
        for (ExaminationRoom r : rooms) {
            roomsDTO.add(new ExaminationRoomDTO(r));
        }

        return roomsDTO;
    }

    public boolean save(ExaminationRoomDTO roomDto) {

        ExaminationRoom room = modelMapper.map(roomDto, ExaminationRoom.class);
        if(repo.findByNumber(roomDto.getNumber()) != null) {

            return false;
        }

        repo.save(room);
        return true;
    }

    @Transactional
    public boolean removeRoom(ExaminationRoomDTO roomDTO) {

        if(repo.findByNumber(roomDTO.getNumber()) != null) {

            if(roomDTO.isAvailable()) {

                return false;
            }

            ExaminationRoom room = modelMapper.map(roomDTO, ExaminationRoom.class);

            repo.deleteByNumber(room.getNumber());

            return true;
        }

        return false;

    }

    public ExaminationRoom findOne(int number) {
        return repo.findByNumber(number);
    }

    public ExaminationRoom update(ExaminationRoom or) {
        return repo.save(or);
    }
}

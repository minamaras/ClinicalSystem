package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.CreateOperationRoomDTO;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.repository.OperationRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ORService {

    @Autowired
    private OperationRoomRepository repo;

    public List<CreateOperationRoomDTO> findAllRooms() {

        List<OR> rooms = repo.findAll();

        List<CreateOperationRoomDTO> roomsDTO = new ArrayList<>();
        for (OR r : rooms) {
            roomsDTO.add(new CreateOperationRoomDTO(r));
        }

        return roomsDTO;
    }
}

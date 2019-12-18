package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.repository.HolidayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<HolidayDTO> findAll() {
        List<Holiday> holidays = holidayRepository.findAll();

        List<HolidayDTO> holidaysDTO = new ArrayList<>();
        for (Holiday h : holidays) {
            holidaysDTO.add(new HolidayDTO(h));
        }

        return holidaysDTO;
    }

    
}

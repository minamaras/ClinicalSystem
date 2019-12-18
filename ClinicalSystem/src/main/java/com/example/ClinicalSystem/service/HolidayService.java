package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.model.Nurse;
import com.example.ClinicalSystem.repository.HolidayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NurseService nurseService;

    public List<HolidayDTO> findAll() {
        List<Holiday> holidays = holidayRepository.findAll();

        List<HolidayDTO> holidaysDTO = new ArrayList<>();
        for (Holiday h : holidays) {
            holidaysDTO.add(new HolidayDTO(h));
        }

        return holidaysDTO;
    }

    public Holiday findOne(String reason){
        return holidayRepository.findByReason(reason);
    }

    public boolean request(String nurseid,HolidayDTO holidayDTO){
        Nurse nurse = nurseService.findByEmail(nurseid);

        Holiday holiday = modelMapper.map(holidayDTO, Holiday.class);
        Set<Holiday> nurseHolidays = nurse.getHolidays();

        for(Holiday h: nurseHolidays){
            if(h.getReason().equals(holiday.getReason()) || h.getStart().equals(holiday.getStart())){
                return false;
            }
        }

        nurse.getHolidays().add(holiday);
        holidayRepository.save(holiday);

        return true;
    }
    /*
    public Holiday save(HolidayDTO holidayDTO){
        Holiday holiday = modelMapper.map(holidayDTO, Holiday.class);
        return holidayRepository.save(holiday);
    }

     */
}

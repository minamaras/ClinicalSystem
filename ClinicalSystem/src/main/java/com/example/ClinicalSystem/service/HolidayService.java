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

import javax.transaction.Transactional;
import java.security.Principal;
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

    @Transactional
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

    @Transactional
    public boolean request(Principal p, HolidayDTO holidayDTO){
        Nurse nurse = nurseService.findByEmail(p.getName());
        holidayDTO.setNurseid(nurse.getEmail());

        Holiday holiday = modelMapper.map(holidayDTO, Holiday.class);
        Set<Holiday> nurseHolidays = nurse.getHolidays();

        for(Holiday h: nurseHolidays){
            if(h.getStart().compareTo(holiday.getStart()) == 0){
                return false;
            }
        }
        holiday.setNurse(nurse);
        //nurse.getHolidays().add(holiday);
        //nurseService.updateNurse(nurse);
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

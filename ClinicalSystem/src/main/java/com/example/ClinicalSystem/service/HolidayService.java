package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.*;
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

    @Autowired
    private UserService userService;


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
        User user = userService.findByUsername(p.getName());
        holidayDTO.setEmail(user.getEmail());

        Holiday holiday = modelMapper.map(holidayDTO, Holiday.class);
        Set<Holiday> holidays = user.getHolidays();

        for(Holiday h: holidays){
            if(h.getStart().compareTo(holiday.getStart()) == 0){
                return false;
            }
        }
        holiday.setUser(user);
        //nurse.getHolidays().add(holiday);
        //nurseService.updateNurse(nurse);
        holiday.setHolidayRequestStatus(HolidayRequestStatus.INPROGRESS);
        holidayRepository.save(holiday);

        return true;
    }
    /*
    public Holiday save(HolidayDTO holidayDTO){
        Holiday holiday = modelMapper.map(holidayDTO, Holiday.class);
        return holidayRepository.save(holiday);
    }

     */

    @Transactional
    public boolean decline(HolidayDTO holidayDTO){
        Long isDeleted = deleteRequest(holidayDTO.getEmail());

        if(isDeleted == 1){
            return true;
        } else {
            return false;
        }
    }

    public Long deleteRequest(String email){
        Long num = holidayRepository.removeByEmail(email);
        return num;
    }
}

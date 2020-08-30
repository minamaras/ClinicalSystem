package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.HolidayDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.HolidayRepository;
import com.example.ClinicalSystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    @Transactional
    public List<Holiday> findAll() {
        List<Holiday> holidays = holidayRepository.findAll();

        holidays.removeIf(h -> h.getHolidayRequestStatus() == HolidayRequestStatus.INPROGRESS);
        return holidays;
    }

    public Holiday findOne(String email){
        return holidayRepository.findByEmail(email);
    }

    @Transactional
    public boolean request(Principal p, Holiday holiday){
        User user = userService.findByUsername(p.getName());

        for(Holiday h: user.getHolidays()){
            if(h.getStart().compareTo(holiday.getStart()) == 0) {
                return false;
            }
        }

        holiday.setUser(user);
        holiday.setHolidayRequestStatus(HolidayRequestStatus.INPROGRESS);

        holidayRepository.save(holiday);
        return true;
    }

    @Transactional
    public boolean decline(HolidayDTO holidayDTO){
        User user = userService.findByUsername(holidayDTO.getEmail());
        try {
            emailService.sendRejectedHolidayAsync(user);
        } catch (Exception e) {
            return false;
        }

        if(changeStatusToRejected(holidayDTO.getEmail())){
            findAll();
            return true;
        } else {
            return false;
        }
    }

    public boolean changeStatusToRejected(String email) {
        Holiday holiday = holidayRepository.findByEmail(email);

        if (holiday == null) {
            return false;
        }

        holiday.setHolidayRequestStatus(HolidayRequestStatus.REJECTED);
        return true;
    }

    public boolean confirm(Holiday holiday) {
        User user = userService.findByUsername(holiday.getEmail());

        if (user == null) {
            return  false;
        }
        
        holiday.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);
        holiday.setUser(user);

        holidayRepository.save(holiday);
        userService.saveHoliday(user, holiday);

        try {
            emailService.sendConfirmHolidayAsync(user);
        } catch (Exception e) {
            return false;
        }

        return  true;

    }
}

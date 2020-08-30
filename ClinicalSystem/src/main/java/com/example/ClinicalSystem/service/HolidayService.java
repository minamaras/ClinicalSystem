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
        holidays.removeIf(h -> h.isOnHoliday());
        return holidays;
    }

    @Transactional
    public boolean request(Principal p, Holiday holiday){
        return !tryToSaveHolidayRequest(holiday, userService.findByUsername(p.getName()));
    }

    private boolean tryToSaveHolidayRequest(Holiday holiday, User user) {
        for(Holiday h: user.getHolidays()){
            if (holiday.checkHolidaySpanValidity(h)) return true;
        }

        setUpHolidayRequestInfo(holiday, user);
        return false;
    }

    private void setUpHolidayRequestInfo(Holiday holiday, User user) {
        holiday.setUser(user);
        holiday.setHolidayRequestStatus(HolidayRequestStatus.INPROGRESS);
        holidayRepository.save(holiday);
    }

    @Transactional
    public boolean decline(Holiday holiday){
        return tryToSendRejectionEmail(userService.findByUsername(holiday.getEmail())) && changeStatusToRejected(holiday);
    }

    private boolean tryToSendRejectionEmail(User user) {
        try {
            emailService.sendRejectedHolidayAsync(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean changeStatusToRejected(Holiday holiday) {
        if (holiday == null) {
            return false;
        }

        holiday.setHolidayRequestStatus(HolidayRequestStatus.REJECTED);
        return true;
    }

    public boolean confirm(Holiday holiday) {
        return tryToSaveNewHoliday(holiday, userService.findByUsername(holiday.getEmail())) &&
                tryToSendConfirmationEmail(userService.findByUsername(holiday.getEmail()));
    }

    private boolean tryToSaveNewHoliday(Holiday holiday, User user) {
        if (user == null) return false;

        setUpHoliday(holiday, user);
        saveHolidayInfoToDatabase(holiday, user);
        return true;
    }

    private void saveHolidayInfoToDatabase(Holiday holiday, User user) {
        holidayRepository.save(holiday);
        userService.saveHoliday(user, holiday);
    }

    private void setUpHoliday(Holiday holiday, User user) {
        holiday.setHolidayRequestStatus(HolidayRequestStatus.ACCEPTED);
        holiday.setUser(user);
    }

    private boolean tryToSendConfirmationEmail(User user) {
        try {
            emailService.sendConfirmHolidayAsync(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

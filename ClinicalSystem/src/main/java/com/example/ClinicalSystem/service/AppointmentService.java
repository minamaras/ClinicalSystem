package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ExamTypeService examTypeService;

    @Autowired
    private OperationRoomService operationRoomService;

    @Autowired
    private ModelMapper modelMapper;

    public List<AppointmentDTO> findAllPredefined() {

        List<Appointment> predefined = appointmentRepository.findAll();

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment n : predefined) {
            appointmentDTOS.add(new AppointmentDTO(n));
        }

        return appointmentDTOS;
    }

    public boolean savePredefined(AppointmentDTO appointmentDTO) throws ParseException {

        if(appointmentRepository.findByName(appointmentDTO.getName()) != null)
            return false;

        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);

        Doctor doctor = doctorService.findOne(appointmentDTO.getDoctorEmail());
        ExamType examType = examTypeService.findOne(appointmentDTO.getExamTypeName());
        OR operationRoom = operationRoomService.findOne(appointmentDTO.getRoomNumber());

        appointment.setDoctor(doctor);
        appointment.setOr(operationRoom);
        appointment.setType(examType);

        String startDate=appointmentDTO.getDate();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = sdf1.parse(appointmentDTO.getDate());
        java.sql.Date finaldate = new java.sql.Date(date.getTime());
        appointment.setStart(finaldate);

        appointment.setClassification(AppointmentClassification.PREDEFINED);
        appointment.setStatus(AppointmentStatus.AVALIABLE);

        appointmentRepository.save(appointment);

        return true;
    }
}

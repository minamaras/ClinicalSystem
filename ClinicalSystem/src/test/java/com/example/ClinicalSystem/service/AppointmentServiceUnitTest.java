package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentServiceUnitTest {

   @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository mockAppointmentRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private EmailService emailService;


    @Before
    public void setup() throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setName("Ocni pregled");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-04");
        appointment.setStart(date1);
        Time time1 = Time.valueOf("10:00:00");
        appointment.setStartTime(time1);
        Time time2 = Time.valueOf("11:00:00");
        appointment.setEndTime(time2);
        appointment.setStatus(AppointmentStatus.HAPPENING);
        appointment.setClassification(AppointmentClassification.PREDEFINED);

        Appointment appointment1 = new Appointment();
        appointment1.setId(2L);
        appointment1.setName("Pregled bubrega");
        Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-01");
        appointment1.setStart(date11);
        Time time11 = Time.valueOf("11:00:00");
        appointment1.setStartTime(time11);
        Time time21 = Time.valueOf("11:40:00");
        appointment1.setEndTime(time21);
        appointment1.setStatus(AppointmentStatus.SHEDULED);
        appointment1.setClassification(AppointmentClassification.PREDEFINED);

        given(mockAppointmentRepository.findByName("Pregled bubrega")
        ).willReturn(new Appointment(2L, "Pregled bubrega", date11, time11, time21, AppointmentStatus.SHEDULED, AppointmentClassification.PREDEFINED));


        Appointment appointment2 = new Appointment();
        appointment2.setId(3L);
        appointment2.setName("Pregled srca");
        Date date12 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-23");
        appointment1.setStart(date12);
        Time time12 = Time.valueOf("15:00:00");
        appointment1.setStartTime(time12);
        Time time22 = Time.valueOf("15:30:00");
        appointment2.setEndTime(time22);
        appointment2.setStatus(AppointmentStatus.SHEDULED);
        appointment2.setClassification(AppointmentClassification.NORMAL);

        given(mockAppointmentRepository.findByName("Pregled srca")
        ).willReturn(new Appointment(3L, "Pregled srca", date12, time12, time22, AppointmentStatus.SHEDULED, AppointmentClassification.NORMAL));

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        appointments.add(appointment1);
        appointments.add(appointment2);

        given(mockAppointmentRepository.findAll()
        ).willReturn(appointments);


    }

    @Test
    public void testFindAllModels() {
        List<Appointment> appointments = appointmentService.findAllModels();
        assertThat(appointments).hasSize(3);
    }

    @Test
    public void testSaveAppointment() throws Exception {

        Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-23");
        Time time11 = Time.valueOf("11:00:00");
        Time time21 = Time.valueOf("11:40:00");

        Appointment appointment2 = mockAppointmentRepository.findByName("Pregled bubrega");
        assertThat(appointment2).isNotNull();

        User user = new User(1L, Role.PATIENT, "Tamara", "Jancic", "tam@gmail.com");
        Assert.assertEquals(Role.PATIENT, user.getRole());

        AppointmentDTO appointmentDTO = new AppointmentDTO(2L, "Pregled bubrega", date11, time11, time21);

        appointmentDTO.setPatientemail(user.getEmail());
        Appointment appointment = new Appointment();
        Patient patient = new Patient();

        when(mockAppointmentRepository.findById(appointmentDTO.getId())).thenReturn(appointment);

        when(patientService.findPatient(appointmentDTO.getPatientemail())).thenReturn(patient);

        when(mockAppointmentRepository.save(appointment)).thenReturn(appointment);

        doNothing().when(emailService).sendEmailAboutAppointment(user,appointment);

        boolean successful = appointmentService.saveAppointment(appointmentDTO);

        Assert.assertEquals(true, successful);


    }


}

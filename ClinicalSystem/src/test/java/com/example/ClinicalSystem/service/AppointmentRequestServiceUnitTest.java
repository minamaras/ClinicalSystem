package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRequestRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRequestServiceUnitTest {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @MockBean
    private AppointmentRequestRepository appointmentRequestRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Before
    public void setup() {
        Patient patient1 = new Patient();
        patient1.setName("Mina");
        patient1.setLastname("Maras");
        patient1.setEmail("mina@gmail.com");

        ExamType examType1 = new ExamType("Stomatoloski pregled", 1500, 30);

        OR or1 = new OR(3,"Soba", examType1);

        Doctor doctor1 = new Doctor();
        doctor1.setName("Tamara");
        doctor1.setLastname("Jancic");
        doctor1.setEmail("tamaraa@gmail.com");
        doctor1.setExamType(examType1);
        doctor1.setSpecialization("Zubar");
        Time stime1 = Time.valueOf("13:00:00");
        Time etime1 = Time.valueOf("20:00:00");
        doctor1.setStart(stime1);
        doctor1.setEnd(etime1);

        Doctor doctor2 = new Doctor();
        doctor2.setName("Jelena");
        doctor2.setLastname("Bojanic");
        doctor2.setEmail("jelenaa@gmail.com");
        doctor2.setExamType(examType1);
        doctor2.setSpecialization("Stomatolog");
        Time stime2 = Time.valueOf("10:00:00");
        Time etime2 = Time.valueOf("20:00:00");
        doctor2.setStart(stime2);
        doctor2.setEnd(etime2);

        Date examDate = Date.valueOf("2020-03-05");
        Time examStart = Time.valueOf("14:00:00");
        Time examEnd = Time.valueOf("14:15:00");

        given(appointmentRequestRepository.findByName("Stomatoloski pregled")
        ).willReturn(new AppointmentRequest(1L,"Stomatoloski pregled", examType1, or1.getId(), patient1, doctor1, examDate, examStart, examEnd));

        given(appointmentRequestService.findByIdModel(1L)
        ).willReturn(Optional.of(new AppointmentRequest(1L, "Stomatoloski pregled", examType1, or1.getId(), patient1, doctor1, examDate, examStart, examEnd)));
    }

    @Test
    public void testFindByNameAppointmentRequest() {
        AppointmentRequest result = appointmentRequestService.findByName("Stomatoloski pregled");
        Assert.assertEquals("Stomatoloski pregled", result.getName());
    }

    @Test
    public void testIsCreated() {
        Optional<AppointmentRequest> apreq = appointmentRequestService.findByIdModel(1L);
        assertThat(apreq).isNotNull();
    }

}

package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.AppointmentRequestRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRequestServiceUnitTest {

    @Autowired
    AppointmentRequestService appointmentRequestService;

    @MockBean
    AppointmentRequestRepository appointmentRequestRepository;

    @Before
    public void setup() {
        Patient patient1 = new Patient();
        patient1.setName("Mina");
        patient1.setLastname("Maras");
        patient1.setEmail("mina@gmail.com");

        ExamType examType1 = new ExamType("Ocni pregled", 1500, 30);

        OR or1 = new OR(3,"Soba", examType1);

        Doctor doctor1 = new Doctor();
        doctor1.setName("Tamara");
        doctor1.setLastname("Jancic");
        doctor1.setEmail("tamaraa@gmail.com");
        doctor1.setExamType(examType1);
        doctor1.setSpecialization("Zubar");

        given(appointmentRequestRepository.findByName("Stomatoloski pregled")
        ).willReturn(new AppointmentRequest("Stomatoloski pregled", examType1, or1.getId(), patient1, doctor1));
    }

    @Test
    public void testFindByNameAppointmentRequest() {
        AppointmentRequest result = appointmentRequestService.findByName("Stomatoloski pregled");
        Assert.assertEquals("Stomatoloski pregled", result.getName());
    }

}

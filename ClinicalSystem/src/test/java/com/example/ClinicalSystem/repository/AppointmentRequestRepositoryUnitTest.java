package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.Doc;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppointmentRequestRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AppointmentRequestRepository appointmentRequestRepository;

    @Before
    public void setUp() {

        Patient patient1 = new Patient();
        patient1.setName("Mina");
        patient1.setLastname("Maras");
        patient1.setEmail("mina@gmail.com");
        entityManager.persist(patient1);

        ExamType examType1 = new ExamType("Ocni pregled", 1500, 30);
        entityManager.merge(examType1);

        Doctor doctor1 = new Doctor();
        doctor1.setName("Tamara");
        doctor1.setLastname("Jancic");
        doctor1.setEmail("tamaraa@gmail.com");
        doctor1.setExamType(examType1);
        doctor1.setSpecialization("Zubar");
        entityManager.persist(doctor1);

        OR or1 = new OR(5L,3,"Soba", examType1);
        entityManager.merge(or1);

        AppointmentRequest appointmentRequest1 = new AppointmentRequest("Pregled ociju", examType1, or1.getId(), patient1, doctor1);
        AppointmentRequest appointmentRequest2 = new AppointmentRequest("Stomatoloski pregled", examType1, or1.getId(), patient1, doctor1);

        Time stime = Time.valueOf("13:00:00");
        appointmentRequest2.setStartTime(stime);

        Time etime = Time.valueOf("15:00:00");
        appointmentRequest2.setEndTime(etime);
        appointmentRequest2.setAppointmentRequestStatus(AppointmentRequestStatus.PATIENTSENT);

        entityManager.merge(appointmentRequest1);
        entityManager.merge(appointmentRequest2);

    }

    @Test
    public void testFindByName() {
        AppointmentRequest appointmentRequest = appointmentRequestRepository.findByName("Stomatoloski pregled");

        Assert.assertEquals("Stomatoloski pregled", appointmentRequest.getName());

    }
}

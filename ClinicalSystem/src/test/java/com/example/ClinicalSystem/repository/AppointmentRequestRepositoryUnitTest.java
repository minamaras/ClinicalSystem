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
       /*User userPatient1 = new User(6L, Role.PATIENT,"Mina", "Maras", "minamarass@gmail.com");
        User userPatient2 = new User(1L, Role.PATIENT,"Petar", "Petrovic", "petrovic@gmail.com");

        entityManager.merge(userPatient1);
        Patient patient1 = new Patient();
        patient1.setName(userPatient1.getName());
        patient1.setLastname(userPatient1.getLastname());
        patient1.setEmail(userPatient1.getEmail());



        User userDoctor1 = new User(2L, Role.DOCTOR, "Tamara", "Jancic", "tamarajancic@gmail.com");
        User userDoctor2 = new User(3L, Role.DOCTOR, "Jelena", "Bojanic", "jelenabojanic@gmail.com");
        User userDoctor3 = new User(4L, Role.DOCTOR, "Tijana", "Maras", "tijanamaras@gmail.com");

        ExamType examType1 = new ExamType("Ocni pregled", 1500, 30);
        entityManager.merge(examType1);

        entityManager.merge(userDoctor1);
        Doctor doctor1 = new Doctor();
        doctor1.setName(userDoctor1.getName());
        doctor1.setLastname(userDoctor1.getLastname());
        doctor1.setEmail(userDoctor1.getEmail());
        doctor1.setExamType(examType1);



        User userClinicAdmin1 = new User(5L, Role.CLINICADMIN, "Petar", "Stupar", "petarstrupar@gmail.com");

        //entityManager.persist(userPatient1);
        entityManager.merge(userPatient2);
        entityManager.merge(userDoctor1);
        entityManager.merge(userDoctor2);
        entityManager.merge(userDoctor3);
        entityManager.merge(userClinicAdmin1);

        Clinic clinic1 = new Clinic("Klinika Novi Sad", "Brace Ribnikar 29", "hahah");

        entityManager.merge(clinic1);


        OR or1 = new OR(3,"Soba", examType1);
        entityManager.merge(or1);



        entityManager.merge(patient1);



        entityManager.merge(doctor1);*/

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

        OR or1 = new OR(3,"Soba", examType1);
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

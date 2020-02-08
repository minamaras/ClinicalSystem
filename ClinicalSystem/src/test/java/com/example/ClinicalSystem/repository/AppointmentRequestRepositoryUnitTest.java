package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.Doc;
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
        User userPatient1 = new User(Role.PATIENT,"Mina", "Maras", "minamaras1997@gmail.com");
        User userPatient2 = new User(Role.PATIENT,"Petar", "Petrovic", "petrovic@gmail.com");

        User userDoctor1 = new User(Role.DOCTOR, "Tamara", "Jancic", "tamarajancic@gmail.com");
        User userDoctor2 = new User(Role.DOCTOR, "Jelena", "Bojanic", "jelenabojanic@gmail.com");
        User userDoctor3 = new User(Role.DOCTOR, "Tijana", "Maras", "tijanamaras@gmail.com");

        User userClinicAdmin1 = new User(Role.CLINICADMIN, "Petar", "Stupar", "petarstrupar@gmail.com");

        Clinic clinic1 = new Clinic("Klinika Novi Sad", "Brace Ribnikar 29");

        ExamType examType1 = new ExamType("Ocni pregled", 1500, 30);

        OR or1 = new OR(3,"Soba", examType1);

        Patient patient1 = (Patient) userPatient1;

        Doctor doctor1 = (Doctor) userDoctor1;

        AppointmentRequest appointmentRequest1 = new AppointmentRequest(1L,examType1,patient1,doctor1, "Pregled ociju");

        entityManager.persist(userPatient1);
        entityManager.persist(userDoctor1);
        entityManager.persist(userDoctor2);
        entityManager.persist(userDoctor3);
        entityManager.persist(userClinicAdmin1);
        entityManager.persist(clinic1);
        entityManager.persist(userPatient2);
        entityManager.persistAndGetId(examType1);
        entityManager.persist(or1);
        entityManager.persist(appointmentRequest1);

    }

    @Test
    public void testFindById() {
        Optional<AppointmentRequest> appointmentRequestList = appointmentRequestRepository.findById(1L);

    }
}

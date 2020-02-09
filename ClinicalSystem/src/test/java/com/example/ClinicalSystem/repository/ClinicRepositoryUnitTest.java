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
import static org.assertj.core.api.Assertions.assertThat;

import javax.print.Doc;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClinicRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ClinicRepository clinicRepository;

    @Before
    public void setUp() {

        Clinic clinic1 = new Clinic();
        clinic1.setName("Andric klinika");
        clinic1.setAdress("Vojvode Bojovica 11");
        clinic1.setDescription("Jedna klinika");
        entityManager.persist(clinic1);

        Clinic clinic2 = new Clinic();
        clinic2.setName("Bozin klininka");
        clinic2.setAdress("Njegoseva 1");
        clinic2.setDescription("Druga klinika");
        entityManager.persist(clinic2);


        Clinic clinic3 = new Clinic();
        clinic3.setName("Dentalend");
        clinic3.setAdress("Fruskogorska 1");
        clinic3.setDescription("Treca klinika");
        entityManager.persist(clinic3);

    }

    @Test
    public void testFindAll() {

        List<Clinic> clinics = clinicRepository.findAll();

        assertThat(clinics).hasSize(7);

        for(Clinic c : clinics){

            System.out.println(c.getName());
        }

    }
}

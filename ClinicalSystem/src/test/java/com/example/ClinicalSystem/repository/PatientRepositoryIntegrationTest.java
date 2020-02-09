package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.service.PatientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientRepositoryIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void testFindByName(){
        Patient patient = patientRepository.findByEmail("jelena.bojanic97@gmail.com");
        assertEquals("jelena", patient.getName());
        assertEquals("Bulevar Oslobodjenja 11", patient.getAdress());
        assertEquals(true, patient.isActive());
        assertEquals("Srbija", patient.getCountry());
    }

}

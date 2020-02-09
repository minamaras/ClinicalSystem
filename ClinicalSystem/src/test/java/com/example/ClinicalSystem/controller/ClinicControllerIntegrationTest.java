package com.example.ClinicalSystem.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.repository.ClinicRepository;
import com.example.ClinicalSystem.service.ClinicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ClinicControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClinicService clinicService;

    @Test
    public void testGetAllClinicsDTO() {

        ResponseEntity<ClinicDTO> responseEntity =  restTemplate.getForEntity("/api/clinics/clinicabout/KlinikaSunce",ClinicDTO.class);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());

    }


}

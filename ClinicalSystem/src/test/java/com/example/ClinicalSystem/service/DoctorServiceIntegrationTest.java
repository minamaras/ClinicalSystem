package com.example.ClinicalSystem.service;

import static org.junit.Assert.assertEquals;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.FilterDTO;
import com.example.ClinicalSystem.service.ClinicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class DoctorServiceIntegrationTest {

    @Autowired
    DoctorService doctorService;

    @Test
    public void testFindAllDoctorsFromAClinic() throws ParseException {


        Set<DoctorDTO> doctors = doctorService.findAllDoctorsFromAClinic("Dentalend");
        assertEquals(3, doctors.size());



    }
}

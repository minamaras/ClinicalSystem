package com.example.ClinicalSystem.service;

import static org.junit.Assert.assertEquals;

import com.example.ClinicalSystem.DTO.ClinicDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ClinicServiceIntegrationTest {

    @Autowired
    ClinicService clinicService;

    @Test
    public void testFilterClinics() throws ParseException {

        FilterDTO filter =  new FilterDTO();
        filter.setDate("2020-03-01");
        filter.setTime("08:00:00");
        filter.setExamtype("Imunoloski pregled");

        List<ClinicDTO> clinics  = clinicService.filterClinics(filter);
        assertEquals(1, clinics.size());

        FilterDTO filter2 =  new FilterDTO();
        filter.setDate("2020-03-01");
        filter.setTime("18:00:00");
        filter.setExamtype("Stomatoloski pregled");

        List<ClinicDTO> clinics2  = clinicService.filterClinics(filter2);
        assertEquals(0, clinics2.size());


    }
}
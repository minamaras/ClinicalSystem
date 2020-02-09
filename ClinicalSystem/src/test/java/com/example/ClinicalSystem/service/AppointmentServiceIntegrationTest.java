package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentDTO;
import com.example.ClinicalSystem.model.Appointment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentServiceIntegrationTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    public void testFindAllModels(){
        List<Appointment> appointments = appointmentService.findAllModels();
        int i = appointments.size();
        assertEquals(14,i);
    }

}

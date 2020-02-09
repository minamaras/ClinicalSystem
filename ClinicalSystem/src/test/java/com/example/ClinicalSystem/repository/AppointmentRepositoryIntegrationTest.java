package com.example.ClinicalSystem.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentClassification;
import com.example.ClinicalSystem.model.AppointmentStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRepositoryIntegrationTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void findByName(){
        Appointment appointment = appointmentRepository.findByName("pregledneki");
        assertEquals("pregledneki",appointment.getName());
        assertEquals(AppointmentStatus.SHEDULED, appointment.getStatus());
        assertThat(appointment.getId()).isEqualTo(-4);
    }

    @Test
    public void findById(){
        Appointment appointment = appointmentRepository.findById(-12);
        assertEquals("preglednekistari", appointment.getName());
        assertEquals(AppointmentClassification.NORMAL, appointment.getClassification());
        assertEquals(AppointmentStatus.HAS_HAPPEND, appointment.getStatus());
    }

}

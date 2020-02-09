package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentClassification;
import com.example.ClinicalSystem.model.AppointmentStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppointmentRepositoryUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Before
    public void setUp() throws ParseException {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setName("Ocni pregled");
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-04");
        appointment.setStart(date1);
        Time time1 = Time.valueOf("10:00:00");
        appointment.setStartTime(time1);
        Time time2 = Time.valueOf("11:00:00");
        appointment.setEndTime(time2);
        appointment.setStatus(AppointmentStatus.HAPPENING);
        appointment.setClassification(AppointmentClassification.PREDEFINED);
        entityManager.merge(appointment);

    }




}

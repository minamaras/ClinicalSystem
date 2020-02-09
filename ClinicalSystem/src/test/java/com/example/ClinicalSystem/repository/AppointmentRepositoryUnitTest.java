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


    @Test
    public void testFindByName() {
        Appointment appointment = appointmentRepository.findByName("Ocni pregled");
        Assert.assertEquals("Ocni pregled", appointment.getName());
    }

    @Test
    public void testFindByNameFail() {
        Appointment appointment = appointmentRepository.findByName("Ocni pregled");
        Assert.assertEquals("Stomatoloski pregled", appointment.getName());
    }

    @Test
    public void testFindById() {
        Appointment appointment = appointmentRepository.findById(1);
        long id1 = Long.valueOf("1");
        assertThat(appointment.getId()).isEqualTo(1);
    }

    @Test
    public void testSave() throws ParseException {

        Appointment appointment1 = new Appointment();
        appointment1.setId(2L);
        appointment1.setName("Pregled bubrega");
        Date date11 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-11");
        appointment1.setStart(date11);
        Time time11 = Time.valueOf("12:00:00");
        appointment1.setStartTime(time11);
        Time time21 = Time.valueOf("13:00:00");
        appointment1.setEndTime(time21);
        appointment1.setStatus(AppointmentStatus.SHEDULED);
        appointment1.setClassification(AppointmentClassification.PREDEFINED);

        Appointment dbappointment = appointmentRepository.save(appointment1);
        assertThat(dbappointment).isNotNull();
    }



}

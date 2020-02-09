package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRequestServiceIntergrationTest {

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private ModelMapper modelMapper;


    @Test
    public void testAssignRoomToRequest() throws ParseException {
        Optional<AppointmentRequest> apreq = appointmentRequestService.findByIdModel(1L);

        AppointmentRequest appointmentRequest = modelMapper.map(apreq, AppointmentRequest.class);

        AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
        appointmentRequestDTO.setId(1L);

        boolean result = appointmentRequestService.assignRoomToRequest("-8", "2020-03-02", "12:30:00", "12:45:00", appointmentRequestDTO);
        Assert.assertEquals(true, result);
    }
}

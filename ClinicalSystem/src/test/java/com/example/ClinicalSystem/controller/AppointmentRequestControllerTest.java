package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.service.AppointmentRequestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentRequestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Test
    public void testIsCreated() {
        ResponseEntity<AppointmentRequestDTO> responseEntity =  restTemplate.getForEntity("/api/appointmentrequest/saveappointmentrequest",AppointmentRequestDTO.class);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

}

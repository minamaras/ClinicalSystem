package com.example.ClinicalSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.PatientService;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<?> register(@RequestBody PatientDTO patientDTO) {

			return  patientService.register(patientDTO);
	}




}

package com.example.ClinicalSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.PatientService;

@RestController
@RequestMapping(value = "api/patients")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<Patient> register(@RequestBody UserDTO user) {

			
			User uEmail = patientService.findUserByEmail(user.getEmail());
			
			if(uEmail != null) {
				
				return null;
			}
		
			
			Patient p = new Patient();
			p.setName(user.getName());
			p.setLastname(user.getLastname());
			p.setPassword(user.getPassword());
			p.setEmail(user.getEmail());
			
			patientService.savePatient(p);
			return new ResponseEntity<>(p,HttpStatus.CREATED);
	}
	

	

}

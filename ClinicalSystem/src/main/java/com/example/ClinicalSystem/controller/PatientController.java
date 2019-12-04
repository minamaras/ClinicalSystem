package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.Authority;
import org.modelmapper.ModelMapper;
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

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	public ResponseEntity<PatientDTO> updateProfile(@RequestBody PatientDTO patientDTO) {

		if(patientService.findPatient(patientDTO.getEmail()) == null){
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {

			Patient patient = patientService.findPatient(patientDTO.getEmail());

			if (patientDTO.getName() != "") {

				patient.setName(patientDTO.getName());
			}

			if (patientDTO.getLastname() != "") {
				patient.setLastname(patientDTO.getLastname());
			}

			if (patientDTO.getAdress() != "") {
				patient.setAdress(patientDTO.getAdress());
			}

			if (patientDTO.getCountry() != "") {
				patient.setCountry(patientDTO.getCountry());
			}

			if (patientDTO.getCity() != "") {
				patient.setCity(patientDTO.getCity());
			}

			if (patientDTO.getPhone() != "") {
				patient.setPhone(patientDTO.getPhone());
			}

			patientService.updatePatient(patient);
			return new ResponseEntity<>(modelMapper.map(patient,PatientDTO.class),HttpStatus.OK);
		}

	}


}

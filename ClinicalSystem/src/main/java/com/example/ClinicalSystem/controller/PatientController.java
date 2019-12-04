package com.example.ClinicalSystem.controller;

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

			if (patientDTO.getName() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setName(p.getName());
			}

			if (patientDTO.getLastname() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setLastname(p.getLastname());
			}

			if (patientDTO.getAdress() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setAdress(p.getAdress());
			}

			if (patientDTO.getCountry() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setCountry(p.getCountry());
			}

			if (patientDTO.getCity() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setCity(p.getCity());
			}

			if (patientDTO.getPhone() == null) {
				Patient p = patientService.findPatient(patientDTO.getEmail());
				patientDTO.setPhone(p.getPhone());
			}

			Patient saved = patientService.updatePatient(modelMapper.map(patientDTO,Patient.class));
			return new ResponseEntity<>(modelMapper.map(saved,PatientDTO.class),HttpStatus.OK);
		}

	}


}

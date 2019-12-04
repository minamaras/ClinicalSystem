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


		//Patient p = modelMapper.map(patientDTO,Patient.class);
		//Patient saved = patientService.updatePatient(p);

		Patient patientUpdated = modelMapper.map(patientDTO, Patient.class);
		Patient saved = patientService.updatePatient(patientUpdated);

		if(saved != null){
			return new ResponseEntity<>(patientDTO,HttpStatus.OK);
		}
		return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


}

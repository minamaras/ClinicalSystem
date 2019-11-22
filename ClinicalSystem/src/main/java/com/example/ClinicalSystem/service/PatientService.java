package com.example.ClinicalSystem.service;

import java.util.Optional;

import com.example.ClinicalSystem.DTO.PatientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.repository.PatientRepository;
import com.example.ClinicalSystem.repository.UserRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<?> register(PatientDTO patientDTO) {

		boolean exists = userService.existsInDB(patientDTO);
		if(!exists){

			Patient p = modelMapper.map(patientDTO,Patient.class);
			patientRepository.save(p);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	
	public Patient savePatient(Patient patient) {
		
		return patientRepository.save(patient);
	}

}

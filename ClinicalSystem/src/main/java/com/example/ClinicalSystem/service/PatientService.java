package com.example.ClinicalSystem.service;

import java.util.Optional;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.PatientRequest;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.repository.PatientRepository;
import com.example.ClinicalSystem.repository.UserRepository;
import com.example.ClinicalSystem.service.PatientRequestService;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientRequestService patientRequestService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean register(PatientDTO patientDTO) {

		UserDTO userDTO = modelMapper.map(patientDTO,UserDTO.class);
		boolean existsInUsers = userService.existsInDB(userDTO);
		boolean registered = false;

		if(!existsInUsers){

			PatientRequestDTO patientRequestDTO = modelMapper.map(patientDTO,PatientRequestDTO.class);
			registered = patientRequestService.AddPatientRequest(patientRequestDTO);			
			return  registered;
		}

		return  registered;
	}

	
	public Patient savePatient(Patient patient) {

		patient.setActive(true);
		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		return patientRepository.save(patient);
	}
	
	public Patient findPatient(String email) {
		
		return 	patientRepository.findByEmail(email);
		
	}

}

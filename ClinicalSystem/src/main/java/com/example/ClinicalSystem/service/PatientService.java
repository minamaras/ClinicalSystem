package com.example.ClinicalSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	private UserRepository userRepository;
	
	
	public User findUserByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
	
	
	public Patient findPatientByEmail(String email) {
		
		return patientRepository.findByEmail(email);
	}

	
	public Patient savePatient(Patient patient) {
		
		return patientRepository.save(patient);
	}

}

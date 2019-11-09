package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import model.Patient;
import model.User;
import repository.PatientRepository;
import repository.UserRepository;

public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private UserRepository userRepository;
		
	public User findUserByUsername(String username) {
		
		return userRepository.findByUsernameIgnoreCase(username);
	}
	
	
	public User findUserByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
	
	
	public Patient findPatientByEmail(String email) {
		
		return patientRepository.findByEmail(email);
	}
	
	public Patient findPatientByUsername(String username) {
		
		return patientRepository.findByUsernameIgnoreCase(username);
	}
	
	public Patient savePatient(Patient patient) {
		
		return patientRepository.save(patient);
	}

}

package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.service.interfaces.PatientRequestServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.PatientRequest;
import com.example.ClinicalSystem.repository.PatientRequestRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientRequestService implements PatientRequestServiceInterface {
	
	@Autowired
	PatientRequestRepository patientRequestRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean emailExistsInDB(PatientRequestDTO patientRequestDTO) {
		
		
		PatientRequest patientRequest = patientRequestRepository.findByEmail(patientRequestDTO.getEmail());
		
		if(patientRequest != null) {
			return true;
		}
		
		return false;
	}
	
	public boolean AddPatientRequest(PatientRequestDTO patientRequestDTO) {
		
		boolean existsInRequests = emailExistsInDB(patientRequestDTO);
		boolean addedRequest = false;
		
		if(!existsInRequests) {
			
			PatientRequest patientRequest = modelMapper.map(patientRequestDTO,PatientRequest.class);
			patientRequest.setPassword(passwordEncoder.encode(patientRequest.getPassword()));
			patientRequestRepository.save(patientRequest);
			addedRequest = true;
			return addedRequest;
		}
		
		return addedRequest;
	}
	
	

}

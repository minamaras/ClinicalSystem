package com.example.ClinicalSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.PatientRequest;
import com.example.ClinicalSystem.repository.PatientRequestRepository;

public class PatientRequestService {
	
	@Autowired
	PatientRequestRepository patientRequestRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
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
			patientRequestRepository.save(patientRequest);
			addedRequest = true;
			return addedRequest;
		}
		
		return addedRequest;
	}
	
	

}

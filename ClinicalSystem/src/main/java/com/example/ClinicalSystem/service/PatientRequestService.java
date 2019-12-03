package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.service.interfaces.PatientRequestServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.PatientRequest;
import com.example.ClinicalSystem.repository.PatientRequestRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientRequestService implements PatientRequestServiceInterface {
	
	@Autowired
	PatientRequestRepository patientRequestRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PatientService patientService;

	@Autowired
	private EmailService emailService;
	
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

    @Override
    public PatientRequestDTO findByEmail(String email) {
	    PatientRequest pr = patientRequestRepository.findByEmail(email);
        PatientRequestDTO requestDTO = modelMapper.map(pr, PatientRequestDTO.class);
        return requestDTO;
    }

    public List<PatientRequestDTO> findAll() {

		List<PatientRequest> requests = patientRequestRepository.findAll();

		List<PatientRequestDTO> requestDTO = new ArrayList<>();
		for (PatientRequest request : requests) {
			requestDTO.add(new PatientRequestDTO(request));
		}

		return requestDTO;
	}


	public boolean confirmUser(PatientRequestDTO requestDTO) {
		Patient patient = modelMapper.map(requestDTO, Patient.class);

		Patient p = patientService.savePatient(patient);

		try {
			emailService.sendNotificaitionAsync(p);
		} catch (Exception e) {
			return false;
		}

		Long isDeleted = deletePatientRequest(p.getEmail());

		if (isDeleted == 1){
			return true;
		} else {
			return false;
		}

	}

	public boolean declineUser(PatientRequestDTO requestDTO, String explanation){
		Patient patient = modelMapper.map(requestDTO, Patient.class);
		Long isDeleted = deletePatientRequest(requestDTO.getEmail());


		try {
			emailService.sendDeclineNotificaitionAsync(patient, explanation);
		} catch (Exception e) {
			return false;
		}

		if(isDeleted == 1){
			return true;
		} else {
			return false;
		}
	}

	public Long deletePatientRequest(String email){
		Long num = patientRequestRepository.removeByEmail(email);
		return num;
	}


}

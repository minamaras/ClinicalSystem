package com.example.ClinicalSystem.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.repository.PatientRepository;
import com.example.ClinicalSystem.repository.UserRepository;
import com.example.ClinicalSystem.service.PatientRequestService;

import javax.transaction.Transactional;

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

	@Autowired
	private NurseService nurseService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private ClinicAdminService clinicAdminService;

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

		patient.setPassword(passwordEncoder.encode(patient.getPassword()));
		return patientRepository.save(patient);
	}

	public Patient updatePatient(Patient patient) {

		//patient.setActive(true);
		return patientRepository.save(patient);
	}

	public Patient findPatient(String email) {
		
		return 	patientRepository.findByEmail(email);
		
	}

	public Patient findVerificationCode(String findVerificationCode) throws ResourceNotFoundException {

		Optional<Patient> patient = patientRepository.findOneByVerificationCode(findVerificationCode);

		if(patient.isPresent())
			return patient.get();
		else
			throw new ResourceNotFoundException("User with this verification code not found!");
	}

	public List<PatientDTO> findAll() {
		List<Patient> patients = patientRepository.findAll();

		List<PatientDTO> patientDTOS = new ArrayList<>();
		for (Patient p : patients) {
			patientDTOS.add(new PatientDTO(p));
		}

		return patientDTOS;
	}

	public List<PatientDTO> findAllFromClinic(Principal p){
		List<Patient> patients = patientRepository.findAll();

		User user = (User) userService.findByUsername(p.getName());
		Clinic myClinic;

		if(user.getRole() == Role.NURSE){
			Nurse nurse = nurseService.findByEmail(user.getEmail());
			myClinic = nurse.getClinic();
		} else if (user.getRole() == Role.DOCTOR){
			Doctor doctor = doctorService.findOne(user.getEmail());
			myClinic = doctor.getClinic();
		} else {
			return null;
		}

		List<PatientDTO> patientDTOS = new ArrayList<>();

		for (Patient patient : patients) {
			for(Clinic c : patient.getClinics()){
				if(c.getId().equals(myClinic.getId())){
					patientDTOS.add(new PatientDTO(patient));
				}
			}
		}

		return patientDTOS;
	}

	public Patient findOneBySocialSecurityNumber(String id) {
		Patient patient = patientRepository.findBySocialSecurityNumber(id);

		return patient;

	}


}

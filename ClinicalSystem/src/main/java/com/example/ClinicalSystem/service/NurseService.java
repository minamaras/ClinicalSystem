package com.example.ClinicalSystem.service;


import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.model.Authority;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Nurse;
import com.example.ClinicalSystem.repository.NurseRepository;

@Service
public class NurseService {

	@Autowired
	private NurseRepository nurseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private ModelMapper modelMapper;
	
	public List<NurseDTO> findAll(){
		List<Nurse> nurses = nurseRepository.findAll();

		List<NurseDTO> nursesDTO = new ArrayList<>();
		for (Nurse n : nurses) {
			nursesDTO.add(new NurseDTO(n));
		}

		return nursesDTO;
	
	}

	public Nurse save(NurseDTO nurseDTO) {

		Nurse nurse = modelMapper.map(nurseDTO, Nurse.class);
		nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));

		Authority authoritie = authorityService.findByname("NURSE");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		nurse.setAuthorities(authorities);

		return nurseRepository.save(nurse);
	}

	public Nurse saveModel(Nurse nurse){
		return nurseRepository.save(nurse);
	}

	public Nurse findByEmail(String email){

		Nurse nurse = nurseRepository.findByEmail(email);
		return  nurse;

	}
	
}

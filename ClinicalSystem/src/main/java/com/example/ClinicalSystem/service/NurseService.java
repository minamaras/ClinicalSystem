package com.example.ClinicalSystem.service;


import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.model.Authority;
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
	
	public List<Nurse> findAll(){
		return nurseRepository.findAll();
	
	}
	
	public Nurse save(Nurse nurse) {

		nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
		Authority authoritie = authorityService.findByname("NURSE");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		nurse.setAuthorities(authorities);


		return nurseRepository.save(nurse);
	}

	public Nurse findByEmail(String email){

		Nurse nurse = nurseRepository.findByEmail(email);
		return  nurse;

	}
	
}

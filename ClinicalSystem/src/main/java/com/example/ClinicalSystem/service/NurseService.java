package com.example.ClinicalSystem.service;


import java.util.List;

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
	
	public List<Nurse> findAll(){
		return nurseRepository.findAll();
	
	}
	
	public Nurse save(Nurse nurse) {

		nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));
		return nurseRepository.save(nurse);
	}
	
}

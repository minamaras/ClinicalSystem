package com.example.ClinicalSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.repository.ClinicRepository;

@Service
public class ClinicService {
	
	@Autowired
	ClinicRepository clinicRepo;
	
	public Clinic addClinic(Clinic clinic) {
		return clinicRepo.save(clinic);
	}

	public List<Clinic> findAllClinics() { return clinicRepo.findAll(); }
	

}

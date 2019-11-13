package com.example.ClinicalSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.repository.ClinicAdminRepository;
import com.example.ClinicalSystem.repository.ClinicRepository;
import com.example.ClinicalSystem.repository.ClinicalCentreAdminRepository;

@Service
public class ClinicalCentreAdminService {

	@Autowired
	private ClinicalCentreAdminRepository clinicalCentreAdminRepository;
	private ClinicAdminRepository clinicAdminRepository;
	private ClinicRepository clinicRepository;
	
	public List<ClinicalCentreAdmin> findAll(){
		return clinicalCentreAdminRepository.findAll();
	}
	
	public ClinicalCentreAdmin save(ClinicalCentreAdmin clinicalCentreAdmin) {
		return clinicalCentreAdminRepository.save(clinicalCentreAdmin);
	}
	
	public ClinicalCentreAdmin findByEmail(String email) {
		return clinicalCentreAdminRepository.findByEmail(email);
	}
	
	public ClinicAdmin addClinicAdmin(ClinicAdmin cs) {
		return clinicAdminRepository.save(cs);
	}
	
	public Clinic addClinic(Clinic clinic) {
		return clinicRepository.save(clinic);
	}
	
}

package com.example.ClinicalSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.repository.ClinicalCentreAdminRepository;

public class ClinicalCentreAdminService {

	@Autowired
	private ClinicalCentreAdminRepository clinicalCentreAdminRepository;
	
	public List<ClinicalCentreAdmin> findAll(){
		return clinicalCentreAdminRepository.findAll();
	}
	
	public ClinicalCentreAdmin save(ClinicalCentreAdmin clinicalCentreAdmin) {
		return clinicalCentreAdminRepository.save(clinicalCentreAdmin);
	}
	
	public ClinicalCentreAdmin findByEmail(String email) {
		return clinicalCentreAdminRepository.findByEmail(email);
	}
}

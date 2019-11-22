package com.example.ClinicalSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.service.ClinicService;

@RestController
public class ClinicController {
	
	@Autowired 
	ClinicService clinicService;
	
	public Clinic addClinic(Clinic clinic) {
		return clinicService.addClinic(clinic);
	}
	
	public List<Clinic> findAllClinics() { return clinicService.findAllClinics(); }

}

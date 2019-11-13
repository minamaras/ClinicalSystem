package com.example.ClinicalSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.repository.ClinicAdminRepository;
import com.example.ClinicalSystem.repository.DoctorRepository;

@Service
public class ClinicAdminService {


	@Autowired
	ClinicAdminRepository clinicAdminRepository;
	
	@Autowired
	DoctorRepository doctorRepository;

	public List<ClinicAdmin> findAll() {
		return clinicAdminRepository.findAll();
	}

	public ClinicAdmin save(ClinicAdmin ca) {
		return clinicAdminRepository.save(ca);
	}

	public Doctor addDoctor(Doctor d) {
		return doctorRepository.save(d);
	}
	
	public ClinicAdmin findByEmail(String email) {
		return clinicAdminRepository.findByEmail(email);
	}

}

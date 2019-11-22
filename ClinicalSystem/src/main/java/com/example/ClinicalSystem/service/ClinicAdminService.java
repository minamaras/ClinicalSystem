package com.example.ClinicalSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.repository.ClinicAdminRepository;

@Service
public class ClinicAdminService {


	@Autowired
	private ClinicAdminRepository clinicAdminRepository;
	
	@Autowired 
	private ModelMapper modelMapper;

	public List<ClinicAdmin> findAll() {
		return clinicAdminRepository.findAll();
	}

	public ClinicAdmin save(ClinicAdminDTO clinicAdminDto) {
		
		ClinicAdmin clinicAdmin = modelMapper.map(clinicAdminDto, ClinicAdmin.class);
		
		clinicAdmin.setName(clinicAdminDto.getName());
		clinicAdmin.setLastname(clinicAdminDto.getLastname());
		clinicAdmin.setEmail(clinicAdminDto.getEmail());
		clinicAdmin.setPassword(clinicAdminDto.getPassword());
		clinicAdmin.setRole(Role.CLINICADMIN);
		
		return clinicAdminRepository.save(clinicAdmin);
	}

	public Doctor addDoctor(Doctor d) {
		return doctorRepository.save(d);
	}

	public ClinicAdmin findByEmail(String email) {
		return clinicAdminRepository.findByEmail(email);
	}
	
	

}

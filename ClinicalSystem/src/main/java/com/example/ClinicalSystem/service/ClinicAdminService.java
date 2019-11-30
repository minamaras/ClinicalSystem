package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.model.Clinic;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<ClinicAdminDTO> findAll() {
		List<ClinicAdmin> clinicAdmins = clinicAdminRepository.findAll();

		List<ClinicAdminDTO> clinicAdminsDTO = new ArrayList<>();
		for (ClinicAdmin c : clinicAdmins) {
			clinicAdminsDTO.add(new ClinicAdminDTO(c));
		}
		
		return clinicAdminsDTO;
	}

	public ClinicAdmin save(ClinicAdminDTO clinicAdminDto) {
		
		ClinicAdmin clinicAdmin = modelMapper.map(clinicAdminDto, ClinicAdmin.class);
		clinicAdmin.setPassword(passwordEncoder.encode(clinicAdmin.getPassword()));

		
		return clinicAdminRepository.save(clinicAdmin);
	}

	public ClinicAdmin findByEmail(String email) {
		return clinicAdminRepository.findByEmail(email);
	}
	
	

}

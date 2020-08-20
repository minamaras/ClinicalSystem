package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.repository.ClinicAdminRepository;

@Service
public class ClinicAdminService {

	@Autowired
	private ClinicAdminRepository clinicAdminRepository;
	@Autowired 
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private ClinicService clinicService;
	@Autowired
	private UserService userService;

	public List<ClinicAdminDTO> findAllClinicAdmins() {
		List<ClinicAdmin> allClinicAdmins = clinicAdminRepository.findAll();

		List<ClinicAdminDTO> clinicAdminsDto = new ArrayList<>();
		for (ClinicAdmin clinicAdmin : allClinicAdmins) {
			clinicAdminsDto.add(convertClinicAdminFromModelToDto(clinicAdmin));
		}
		
		return clinicAdminsDto;
	}

	private ClinicAdminDTO convertClinicAdminFromModelToDto(ClinicAdmin clinicAdmin) {
		return modelMapper.map(clinicAdmin, ClinicAdminDTO.class);
	}

	public List<ClinicAdminDTO> findAvailableAdmins() {
		List<ClinicAdmin> clinicAdmins = clinicAdminRepository.findAll();

		List<ClinicAdminDTO> clinicAdminsDTO = new ArrayList<>();

		for (ClinicAdmin c : clinicAdmins) {
			if(c.getClinic() == null) {
				clinicAdminsDTO.add(convertClinicAdminFromModelToDto(c));
			}
		}

		return clinicAdminsDTO;
	}

	public boolean save(ClinicAdminDTO clinicAdminDto, String clinicid) {

		if(userService.findByUsername(clinicAdminDto.getEmail()) != null){
			return false;
		}
		ClinicDTO clinicdto = clinicService.findClinic(clinicid);
		
		ClinicAdmin clinicAdmin = modelMapper.map(clinicAdminDto, ClinicAdmin.class);
		clinicAdmin.setPassword(passwordEncoder.encode(clinicAdmin.getPassword()));
		if(clinicdto != null){
			Clinic clinic = modelMapper.map(clinicdto, Clinic.class);
			clinicAdmin.setClinic(clinic);
			clinic.getClinicAdmins().add(clinicAdmin);
		}

		Authority authoritie = authorityService.findByname("CLINICADMIN");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		clinicAdmin.setAuthorities(authorities);
		
		clinicAdminRepository.save(clinicAdmin);
		return true;
	}

	public ClinicAdmin saveModel(ClinicAdmin clinicAdmin){
		return clinicAdminRepository.save(clinicAdmin);
	}
	
	public ClinicAdmin findByEmail(String email) {
		return clinicAdminRepository.findByEmail(email);
	}

	public ClinicAdmin updateClinicAdmin(ClinicAdmin admin) {
		return clinicAdminRepository.save(admin);
	}

}
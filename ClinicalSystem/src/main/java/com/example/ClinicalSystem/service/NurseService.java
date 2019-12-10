package com.example.ClinicalSystem.service;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	public List<NurseDTO> findAll(){
		List<Nurse> nurses = nurseRepository.findAll();

		List<NurseDTO> nursesDTO = new ArrayList<>();
		for (Nurse n : nurses) {
			nursesDTO.add(new NurseDTO(n));
		}

		return nursesDTO;
	
	}

	public Nurse save(NurseDTO nurseDTO, Principal p) {

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinicAdm = cAdmin.getClinic();


		Nurse nurse = modelMapper.map(nurseDTO, Nurse.class);
		nurse.setClinic(clinicAdm);
		clinicAdm.getNurses().add(nurse);
		nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));

		Authority authoritie = authorityService.findByname("NURSE");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		nurse.setAuthorities(authorities);

		return nurseRepository.save(nurse);
	}

	public Nurse saveModel(Nurse nurse){
		return nurseRepository.save(nurse);
	}

	public Nurse findByEmail(String email){

		Nurse nurse = nurseRepository.findByEmail(email);
		return  nurse;

	}
	
}

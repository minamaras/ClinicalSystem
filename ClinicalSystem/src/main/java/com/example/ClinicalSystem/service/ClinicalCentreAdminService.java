package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.model.Authority;
import com.example.ClinicalSystem.model.Patient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.repository.ClinicalCentreAdminRepository;

@Service
public class ClinicalCentreAdminService {

	@Autowired
	private ClinicalCentreAdminRepository clinicalCentreAdminRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmailService emailService;

  @Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private UserService userService;

	
	public List<ClinicalCentreAdminDTO> findAll(){
		
		List<ClinicalCentreAdmin> clinicAdmins = clinicalCentreAdminRepository.findAll();

		List<ClinicalCentreAdminDTO> clinicAdminsDTO = new ArrayList<>();
		for (ClinicalCentreAdmin c : clinicAdmins) {
			clinicAdminsDTO.add(new ClinicalCentreAdminDTO(c));
		}
		
		return clinicAdminsDTO;
	}
	
	public boolean save(ClinicalCentreAdminDTO clinicalCentreAdminDto) {

		if(userService.findByUsername(clinicalCentreAdminDto.getEmail()) != null){
			return false;
		}

		ClinicalCentreAdmin ccAdmin = modelMapper.map(clinicalCentreAdminDto, ClinicalCentreAdmin.class);
		ccAdmin.setPassword(passwordEncoder.encode(ccAdmin.getPassword()));
		Authority authoritie = authorityService.findByname("CLINICALCENTREADMIN");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		ccAdmin.setAuthorities(authorities);
		clinicalCentreAdminRepository.save(ccAdmin);

		return true;
	}

	public ClinicalCentreAdmin updateAdmin(ClinicalCentreAdmin ccAdmin) {

		return clinicalCentreAdminRepository.save(ccAdmin);
	}
	
	public ClinicalCentreAdmin findByEmail(String email) {
		return clinicalCentreAdminRepository.findByEmail(email);
	}
	
	
}

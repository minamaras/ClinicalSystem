package com.example.ClinicalSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.repository.ClinicalCentreAdminRepository;

@Service
public class ClinicalCentreAdminService {

	@Autowired
	private ClinicalCentreAdminRepository clinicalCentreAdminRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	
	public List<ClinicalCentreAdmin> findAll(){
		return clinicalCentreAdminRepository.findAll();
	}
	
	public ClinicalCentreAdmin save(ClinicalCentreAdminDTO clinicalCentreAdminDto) {
		
		ClinicalCentreAdmin ccAdmin = modelMapper.map(clinicalCentreAdminDto, ClinicalCentreAdmin.class);
	
		ccAdmin.setId(clinicalCentreAdminDto.getId());
		ccAdmin.setName(clinicalCentreAdminDto.getName());
		ccAdmin.setLastname(clinicalCentreAdminDto.getLastname());
		ccAdmin.setEmail(clinicalCentreAdminDto.getEmail());
		
		return clinicalCentreAdminRepository.save(ccAdmin);
	}
	
	public ClinicalCentreAdmin findByEmail(String email) {
		return clinicalCentreAdminRepository.findByEmail(email);
	}
	
	
}

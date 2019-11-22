package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

	
	public List<ClinicalCentreAdminDTO> findAll(){
		
		List<ClinicalCentreAdmin> clinicAdmins = clinicalCentreAdminRepository.findAll();

		List<ClinicalCentreAdminDTO> clinicAdminsDTO = new ArrayList<>();
		for (ClinicalCentreAdmin c : clinicAdmins) {
			clinicAdminsDTO.add(new ClinicalCentreAdminDTO(c));
		}
		
		return clinicAdminsDTO;
	}
	
	public ClinicalCentreAdmin save(ClinicalCentreAdminDTO clinicalCentreAdminDto) {
		
		ClinicalCentreAdmin ccAdmin = modelMapper.map(clinicalCentreAdminDto, ClinicalCentreAdmin.class);
		
		return clinicalCentreAdminRepository.save(ccAdmin);
	}
	
	public ClinicalCentreAdmin findByEmail(String email) {
		return clinicalCentreAdminRepository.findByEmail(email);
	}
	
	
}

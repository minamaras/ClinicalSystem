package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.repository.ClinicRepository;

import javax.transaction.Transactional;

@Service
public class ClinicService {
	
	@Autowired
	ClinicRepository clinicRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	public Clinic addClinic(ClinicDTO clinicDto) {
		
		Clinic clinic =  modelMapper.map(clinicDto, Clinic.class);
		
		return clinicRepo.save(clinic);
	}

	public List<Clinic> findAllClinics() {
		
		List<Clinic> clinics = clinicRepo.findAll();
	/*
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for (Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}
		*/
		return clinics;
	}

	public ClinicDTO findClinic(String name) {
		Clinic clinic = clinicRepo.findByName(name);
		ClinicDTO clinicDTO = modelMapper.map(clinic, ClinicDTO.class);
		return clinicDTO;
	}

	@Transactional
	public boolean addAdminToClinic(ClinicDTO clinicDTO, ClinicAdminDTO cadminDTO){
		Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);
		ClinicAdmin cAdmin = modelMapper.map(cadminDTO, ClinicAdmin.class);


		clinic.getClinicAdmins().add(cAdmin);
		cAdmin.setClinics(clinic);


		if(cAdmin.getClinic() != null){
			return true;
		} else {
			return false;
		}
	}
	

}

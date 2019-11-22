package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.repository.ClinicRepository;

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

	public List<ClinicDTO> findAllClinics() { 
		
		List<Clinic> clinics = clinicRepo.findAll();

		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for (Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}
		
		return clinicsDTO;
	}
	

}

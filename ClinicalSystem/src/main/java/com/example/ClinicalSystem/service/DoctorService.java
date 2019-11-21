package com.example.ClinicalSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}
	
	public Doctor save(DoctorDTO doctorDto) {
		
		Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
		
		doctor.setName(doctorDto.getName());
		doctor.setLastname(doctorDto.getLastname());
		doctor.setEmail(doctorDto.getEmail());
		doctor.setPassword(doctorDto.getPassword());
		doctor.setSpecialization(doctorDto.getSpecialization());
		doctor.setRating(doctorDto.getRating());
		doctor.setRole(Role.DOCTOR);
		
        return doctorRepository.save(doctor);
    }

}

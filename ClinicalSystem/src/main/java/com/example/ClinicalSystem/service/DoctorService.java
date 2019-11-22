package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<DoctorDTO> findAll() {
		
		List<Doctor> doctors = doctorRepository.findAll();

		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}
		
		return doctorsDTO;
	}
	
	public Doctor saveDoctor(DoctorDTO doctorDto) {
		
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

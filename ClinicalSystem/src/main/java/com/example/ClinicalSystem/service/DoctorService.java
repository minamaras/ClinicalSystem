package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<DoctorDTO> findAll() {
		
		List<Doctor> doctors = doctorRepository.findAll();

		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}
		
		return doctorsDTO;
	}
	
	public Doctor saveDoctor(DoctorDTO doctorDto) {
		
		UserDTO userDto = modelMapper.map(doctorDto, UserDTO.class);
		if(userService.existsInDB(userDto)) {
			return null;
		}
		
		Doctor doctor = modelMapper.map(doctorDto, Doctor.class);
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

        return doctorRepository.save(doctor);
    }

}

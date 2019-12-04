package com.example.ClinicalSystem.service;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.model.Authority;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.repository.DoctorRepository;

import javax.transaction.Transactional;

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

	@Autowired
	private AuthorityService authorityService;

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
		Authority authoritie = authorityService.findByname("DOCTOR");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		doctor.setAuthorities(authorities);

		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

        return doctorRepository.save(doctor);
    }

    @Transactional
    public boolean removeDoctor(DoctorDTO doctorDto) {
		UserDTO userDto = modelMapper.map(doctorDto, UserDTO.class);

		if(userService.existsInDB(userDto)) {
			Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

			if(doctor.getAppointments().size() >= 1) {
				return  false;
			}

			doctorRepository.deleteByEmail(doctor.getEmail());

			return true;
		}

		return false;

	}

	public Doctor findOne(String email) {
		return doctorRepository.findByEmail(email);
	}
}

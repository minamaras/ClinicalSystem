package com.example.ClinicalSystem.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.*;

import java.util.*;

import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

	@Autowired
	private ClinicService clinicService;


	public Set<DoctorDTO> findAll(Principal p) {

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinic = cAdmin.getClinic();

		Set<Doctor> doctors = clinic.getDoctors();

		Set<DoctorDTO> doctorsDTO = new HashSet<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}
		
		return doctorsDTO;
	}

	public Doctor updateDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	public Doctor save(DoctorDTO doctorDTO, Principal p) {

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinic = cAdmin.getClinic();


		Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

		if(doctor.getStart().compareTo(doctor.getEnd()) > 0 || doctor.getStart().compareTo(doctor.getEnd()) == 0 ) {
			return null;
		}

		if(clinic != null) {
			doctor.setClinic(clinic);
			clinic.getDoctors().add(doctor);
		}

		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

		Authority authoritie = authorityService.findByname("DOCTOR");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		doctor.setAuthorities(authorities);

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

	public Doctor findOneById(Long id) {

		Optional<User> user = userService.findById(id);
		User u = user.get();
		Doctor doctor = doctorRepository.findByEmail(u.getEmail());

		return doctor;
	}

	public Set<DoctorDTO> findAllDoctorsFromAClinic(String clinicname){

		HashSet<DoctorDTO> doctorsret = new HashSet<>();
		Clinic clinic = clinicService.findName(clinicname);

		Set<Doctor> docs =clinic.getDoctors();
		Set<Long> setOfIds = new HashSet<>();
		for(Doctor doctor : docs){
			setOfIds.add(doctor.getId());
			DoctorDTO doctorDTO = modelMapper.map(doctor,DoctorDTO.class);
			ExamTypeDTO examTypeDTO = modelMapper.map(doctor.getExamType(),ExamTypeDTO.class);
			doctorDTO.setClinicid(clinic.getId());
			doctorDTO.setExamType(examTypeDTO);

			doctorsret.add(doctorDTO);

		}

		return  doctorsret;
	}
}

package com.example.ClinicalSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.service.ClinicAdminService;
import com.example.ClinicalSystem.service.DoctorService;


@RestController
@RequestMapping(value = "api/clinicadmin")
public class ClinicAdminController {

	@Autowired
	ClinicAdminService clinicAdminService;
	DoctorService doctorService;
	
	@PostMapping(value = "/add", consumes = "application/json")
	public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
		
		Doctor doctor = new Doctor();
		doctor.setName(doctorDTO.getFirstName());
		doctor.setLastname(doctorDTO.getLastName());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setClinic(doctorDTO.getClinic());
		
		doctor = doctorService.save(doctor);
		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.CREATED);
	}
}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.service.ClinicAdminService;
import com.example.ClinicalSystem.service.DoctorService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/clinicadmin")
public class ClinicAdminController {

	@Autowired
	private ClinicAdminService clinicAdminService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, value = "/saveDoctor")
	public ResponseEntity<Doctor> saveDoctor(@RequestBody DoctorDTO doctorDTO) {

		User uEmail = userService.findByEmail(doctorDTO.getEmail());

		if(uEmail != null) {

			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		Doctor doctor = new Doctor();
		doctor.setName(doctorDTO.getName());
		doctor.setLastname(doctorDTO.getLastname());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setSpecialization(doctorDTO.getSpecialization());
		doctor.setRating(doctorDTO.getRating());

		doctorService.save(doctor);
		return new ResponseEntity<>(doctor, HttpStatus.CREATED);
	}
}

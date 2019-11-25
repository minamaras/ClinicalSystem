package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.service.DoctorService;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@RequestMapping(method = RequestMethod.GET, value = "/alldoctors")
	public ResponseEntity<List<DoctorDTO>> getAllDoctors() {

		List<DoctorDTO> doctors = doctorService.findAll();

		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/savedoctor")
	public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
		
		Doctor d = doctorService.saveDoctor(doctorDTO);
		
		if( d == null) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<>(doctorDTO, HttpStatus.CREATED);
	}


}

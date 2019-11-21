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

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.service.DoctorService;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<DoctorDTO>> getAllDoctors() {

		List<Doctor> doctors = doctorService.findAll();

		// convert students to DTOs
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}

		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}
	
	//@RequestMapping(method = RequestMethod.POST, value = "/saveDoctor")
	public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {

		doctorService.save(doctorDTO);
		return new ResponseEntity<>(doctorDTO, HttpStatus.CREATED);
	}


}

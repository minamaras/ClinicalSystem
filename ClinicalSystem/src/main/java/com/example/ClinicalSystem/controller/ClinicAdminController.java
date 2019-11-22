package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.service.ClinicAdminService;
import com.example.ClinicalSystem.service.DoctorService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/clinicAdmin")
public class ClinicAdminController {

	@Autowired
	private ClinicAdminService clinicAdminService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/addClinicAdmin")
	public ResponseEntity<ClinicAdminDTO> addClinicAdmin(@RequestBody ClinicAdminDTO clinicAdminDTO) {

		clinicAdminService.save(clinicAdminDTO);
		return new ResponseEntity<>(clinicAdminDTO, HttpStatus.CREATED);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/allClinicAdmins")
	public ResponseEntity<List<ClinicAdminDTO>> getAllClinicAdmins() {

		List<ClinicAdminDTO> clinicAdmins = clinicAdminService.findAll();

		return new ResponseEntity<>(clinicAdmins, HttpStatus.OK);
	}

	
}

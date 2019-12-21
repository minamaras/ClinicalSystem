package com.example.ClinicalSystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.model.Nurse;
import com.example.ClinicalSystem.service.NurseService;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/nurses")
public class NurseController {

	@Autowired
	private NurseService nurseService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<List<NurseDTO>> getAllNurses() {

		List<NurseDTO> nursesdto = nurseService.findAll();

		return new ResponseEntity<>(nursesdto, HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.POST, value = "/addnurse")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<NurseDTO> addNurse(@RequestBody NurseDTO nurseDTO, Principal p) {

		nurseService.save(nurseDTO, p);
		return new ResponseEntity<>(nurseDTO, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	@PreAuthorize("hasAuthority('NURSE')")
	public ResponseEntity<NurseDTO> updateProfile(@RequestBody NurseDTO nurseDTO) {

		if (nurseService.findByEmail(nurseDTO.getEmail()) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {

			Nurse nurse = nurseService.findByEmail(nurseDTO.getEmail());

			if (nurseDTO.getName() != "") {

				nurse.setName(nurseDTO.getName());
			}

			if (nurseDTO.getLastname() != "") {
				nurse.setLastname(nurseDTO.getLastname());
			}

			nurseService.updateNurse(nurse);
			NurseDTO nursedto1 = modelMapper.map(nurse, NurseDTO.class);
			nursedto1.setClinicid(nurse.getClinic().getName());

			return new ResponseEntity<>(nursedto1, HttpStatus.OK);
		}

	}
}

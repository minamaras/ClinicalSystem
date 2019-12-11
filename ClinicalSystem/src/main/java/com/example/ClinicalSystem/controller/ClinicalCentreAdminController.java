package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.model.*;
import com.sun.xml.bind.v2.schemagen.episode.SchemaBindings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.service.ClinicAdminService;
import com.example.ClinicalSystem.service.ClinicalCentreAdminService;
import com.example.ClinicalSystem.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/clinicalcentreadmins")
public class ClinicalCentreAdminController {



	@Autowired
	private ClinicalCentreAdminService ccaService;

	@Autowired
	private ModelMapper modelMapper;



	@GetMapping(value = "/all")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<List<ClinicalCentreAdminDTO>> getAllccAdmins(){
		List<ClinicalCentreAdminDTO> ccas = ccaService.findAll();

		return new ResponseEntity<>(ccas, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/addccadmin")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<ClinicalCentreAdminDTO> addccAdmin(@RequestBody ClinicalCentreAdminDTO ccAdminDTO) {
		ccaService.save(ccAdminDTO);
		return new ResponseEntity<>(ccAdminDTO,HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<ClinicalCentreAdminDTO> updateProfile(@RequestBody ClinicalCentreAdminDTO ccAdminDTO) {

		if (ccaService.findByEmail(ccAdminDTO.getEmail()) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {

			ClinicalCentreAdmin ccAdmin = ccaService.findByEmail(ccAdminDTO.getEmail());

			if (ccAdminDTO.getName() != "") {

				ccAdmin.setName(ccAdminDTO.getName());
			}

			if (ccAdminDTO.getLastname() != "") {
				ccAdmin.setLastname(ccAdminDTO.getLastname());
			}

			ccaService.updateAdmin(ccAdmin);

			return new ResponseEntity<>(modelMapper.map(ccAdmin,ClinicalCentreAdminDTO.class), HttpStatus.OK);
		}
	}

}

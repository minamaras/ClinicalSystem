package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
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
	private ModelMapper modelMapper;


	@RequestMapping(method = RequestMethod.POST, value = "/addclinicadmin/{clinicid}")
	@PreAuthorize("hasAnyAuthority('CLINICADMIN','CLINICALCENTREADMIN')")
	public ResponseEntity<?> addClinicAdmin(@PathVariable String clinicid, @RequestBody ClinicAdminDTO clinicAdminDTO) {

		boolean isAdded = clinicAdminService.save(clinicAdminDTO, clinicid);
		if(!isAdded){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(HttpStatus.CREATED);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@PreAuthorize("hasAnyAuthority('CLINICADMIN','CLINICALCENTREADMIN')")
	public ResponseEntity<List<ClinicAdminDTO>> getAllClinicAdmins() {

		List<ClinicAdminDTO> clinicAdmins = clinicAdminService.findAll();

		return new ResponseEntity<>(clinicAdmins, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/available")
	@PreAuthorize("hasAnyAuthority('CLINICADMIN','CLINICALCENTREADMIN')")
	public ResponseEntity<List<ClinicAdminDTO>> getAvailableAdmins() {

		List<ClinicAdminDTO> clinicAdmins = clinicAdminService.findAvailableAdmins();

		return new ResponseEntity<>(clinicAdmins, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<ClinicAdminDTO> updateProfile(@RequestBody ClinicAdminDTO clinicAdminDTO) {

		if(clinicAdminService.findByEmail(clinicAdminDTO.getEmail()) == null){
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {

			ClinicAdmin admin = clinicAdminService.findByEmail(clinicAdminDTO.getEmail());

			if (clinicAdminDTO.getName() != "") {

				admin.setName(clinicAdminDTO.getName());
			}

			if (clinicAdminDTO.getLastname() != "") {
				admin.setLastname(clinicAdminDTO.getLastname());
			}

			clinicAdminService.updateClinicAdmin(admin);
			return new ResponseEntity<>(modelMapper.map(admin,ClinicAdminDTO.class),HttpStatus.OK);
		}

	}




}

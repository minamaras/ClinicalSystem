package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.service.ClinicService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/clinics")
public class ClinicController {

	@Autowired
	ClinicService clinicService;

	@Autowired
	ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.POST, value = "/addclinic")
	public ResponseEntity<ClinicDTO> addClinic(@RequestBody ClinicDTO clinicDTO) {

		clinicService.addClinic(clinicDTO);
		return new ResponseEntity<>(clinicDTO,HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/allclinics")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {

		List<ClinicDTO> clinics = clinicService.findAllClinics();

		return new ResponseEntity<>(clinics, HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.PUT, value = "/updateclinic")
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO) {

		if(clinicService.findClinic(clinicDTO) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		clinicService.updateClinic(clinicDTO);
		return new ResponseEntity<>(clinicDTO,HttpStatus.OK);

	}



	@RequestMapping(method = RequestMethod.POST, value = "/connectadmin/{clinicid}")
	public ResponseEntity<ClinicDTO> addAdmin(@PathVariable String clinicid, @RequestBody ClinicAdminDTO cadminDTO){
		ClinicDTO clinicdto = clinicService.findClinic(clinicid);
		System.out.println(clinicdto.getName());
		boolean isConnected = clinicService.addAdminToClinic(clinicdto, cadminDTO);

		if(isConnected) {
			return new ResponseEntity<>(clinicdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(clinicdto, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/connectwithdoctors/{clinicid}")
	public ResponseEntity<ClinicDTO> addDoctors(@PathVariable String clinicid, @RequestBody List<DoctorDTO> doctorDtos) {
		ClinicDTO clinicdto = clinicService.findClinic(clinicid);

		boolean isConnected = clinicService.addDoctorsToClinic(clinicdto, doctorDtos);

		if(isConnected) {
			return new ResponseEntity<>(clinicdto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(clinicdto, HttpStatus.BAD_REQUEST);
		}

	}





}

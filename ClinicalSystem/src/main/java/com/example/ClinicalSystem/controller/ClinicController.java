package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<ClinicDTO> addClinic(@RequestBody ClinicDTO clinicDTO) {

		clinicService.addClinic(modelMapper.map(clinicDTO,Clinic.class));
		return new ResponseEntity<>(clinicDTO,HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/allclinics")
	@PreAuthorize("hasAnyAuthority('CLINICALCENTREADMIN','PATIENT')")
	public ResponseEntity<List<Clinic>> getAllClinics() {

		List<Clinic> clinics = clinicService.findAllClinics();

		return new ResponseEntity<>(clinics, HttpStatus.OK);
	}


	/*@RequestMapping(method = RequestMethod.GET, value = "/allclinics")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {

		List<Clinic> clinics = clinicService.findAllClinics();

		return new ResponseEntity<>(clinics, HttpStatus.OK);
	}*/


	/*@RequestMapping(method = RequestMethod.PUT, value = "/updateclinic")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<ClinicDTO> updateClinic(@RequestBody ClinicDTO clinicDTO) {

		if(clinicService.findClinic(clinicDTO) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		clinicService.updateClinic(clinicDTO);
		return new ResponseEntity<>(clinicDTO,HttpStatus.OK);

	}*/



	@RequestMapping(method = RequestMethod.POST, value = "/connectadmin/{clinicid}")
	@PreAuthorize("hasAuthority('CLINICALCENTREADMIN')")
	public ResponseEntity<ClinicDTO> addAdmin(@PathVariable String clinicid, @RequestBody ClinicAdminDTO cadminDTO){
		ClinicDTO clinicdto = clinicService.findClinic(clinicid);

		boolean isConnected = clinicService.addAdminToClinic(clinicdto, cadminDTO);
		ClinicDTO dto = clinicService.findClinic(clinicid);
		if(isConnected) {
			return new ResponseEntity<>(dto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
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

	@RequestMapping(method = RequestMethod.GET, value = "/clinicinfo")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<ClinicDTO> currentClinicInfo() {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		ClinicAdmin clinicAdmin = (ClinicAdmin) a.getPrincipal();

		Clinic clinic = clinicAdmin.getClinic();

		return new ResponseEntity<>(modelMapper.map(clinic, ClinicDTO.class), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/clinicabout/{clinicname}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<ClinicDTO> AboutClinic(@PathVariable String clinicname) {

		Clinic clinic = clinicService.findName(clinicname);
		if(clinic != null) {

			ClinicDTO clinicDTO = modelMapper.map(clinic, ClinicDTO.class);
			Set<Doctor> docs =clinic.getDoctors();
			Set<Long> setOfIds = new HashSet<>();
			for(Doctor doctor : docs){
				setOfIds.add(doctor.getId());
			}
			clinicDTO.setDoctorsId(setOfIds);
			return new ResponseEntity<>(clinicDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<ClinicAdminDTO> update(@RequestBody ClinicDTO clinicDTO) {

		if(clinicService.findClinic(clinicDTO.getName()) == null){
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {

			Clinic clinic = clinicService.findName(clinicDTO.getName());

			if (clinicDTO.getAdress() != "") {

				clinic.setDescription(clinicDTO.getDescription());
			}

			if (clinicDTO.getDescription() != "") {
				clinic.setAdress(clinicDTO.getAdress());
			}

			clinicService.updateClinic(clinic);
			return new ResponseEntity<>(modelMapper.map(clinic,ClinicAdminDTO.class),HttpStatus.OK);
		}

	}







}

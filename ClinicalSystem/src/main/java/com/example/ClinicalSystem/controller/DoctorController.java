package com.example.ClinicalSystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.ClinicalSystem.DTO.ExamTypeDTO;
import com.example.ClinicalSystem.DTO.NurseDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.service.DoctorService;

import javax.transaction.Transactional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@Autowired
	ModelMapper modelMapper;

	@RequestMapping(method = RequestMethod.GET, value = "/alldoctors")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<Set<DoctorDTO>> getAllDoctors(Principal p) {

		Set<DoctorDTO> doctors = doctorService.findAll(p);

		return new ResponseEntity<>(doctors, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/savedoctor")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO, Principal p) {

		doctorService.save(doctorDTO, p);
		return new ResponseEntity<>(doctorDTO, HttpStatus.CREATED);

	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, value = "/deletedoctor")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<?> deleteDoctor(@RequestBody DoctorDTO doctorDto){

		if(doctorService.findOne(doctorDto.getEmail()) != null) {
			if (doctorService.removeDoctor(doctorDto))
				return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/doctorabout/{id}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<DoctorDTO> AboutDoctor(@PathVariable String id) {


		Doctor doctor = doctorService.findOneById(Long.parseLong(id));
		if(doctor != null) {

			DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
			Clinic clinic = doctor.getClinic();
			doctorDTO.setClinicid(clinic.getId());
			doctorDTO.setClinicname(clinic.getName());
			ExamTypeDTO examTypeDTO = modelMapper.map(doctor.getExamType(), ExamTypeDTO.class);
			doctorDTO.setExamType(examTypeDTO);

			return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/aboutclinicdoctors/{clinicname}")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<Set<DoctorDTO>>DoctorsOfClinic(@PathVariable String clinicname) {


		Set<DoctorDTO> doctors = doctorService.findAllDoctorsFromAClinic(clinicname);
		return new ResponseEntity<>(doctors, HttpStatus.OK);

	}


	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<DoctorDTO> updateProfile(@RequestBody DoctorDTO doctorDTO) {

		if(doctorService.findOne(doctorDTO.getEmail()) == null){
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {

			Doctor doctor = doctorService.findOne(doctorDTO.getEmail());

			if (doctorDTO.getName() != "") {

				doctor.setName(doctorDTO.getName());
			}

			if (doctorDTO.getLastname() != "") {
				doctor.setLastname(doctorDTO.getLastname());
			}

			if(doctorDTO.getSpecialization() != "") {
				doctor.setSpecialization(doctorDTO.getSpecialization());
			}

			if(doctorDTO.getRating() < 0 && doctorDTO.getRating() > 10) {
				doctor.setRating(doctorDTO.getRating());
			}
			DoctorDTO drdto = modelMapper.map(doctor,DoctorDTO.class);
			drdto.setClinicid(doctor.getClinic().getId());
			drdto.setClinicname(doctor.getClinic().getName());
			doctorService.updateDoctor(doctor);

			return new ResponseEntity<>(drdto,HttpStatus.OK);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getdates")
	@PreAuthorize("hasAuthority('DOCTOR')")
	public ResponseEntity<DoctorDTO> getDoctorsShift(Principal p) {

		DoctorDTO doctorDTO = doctorService.findOneByPrincipal(p);

		return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
	}

}

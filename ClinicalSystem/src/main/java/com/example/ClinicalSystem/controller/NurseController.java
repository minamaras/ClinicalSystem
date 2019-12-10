package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
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

	@GetMapping(value = "/all")
	public ResponseEntity<List<NurseDTO>> getAllNurses(){
		List<Nurse> nurses = nurseService.findAll();

		List<NurseDTO> nursesDTO = new ArrayList<>();

		for(Nurse nurse : nurses) {
			NurseDTO nurseDTOpomocna = modelMapper.map(nurse,NurseDTO.class);
			nursesDTO.add(nurseDTOpomocna);
		}
		return new ResponseEntity<>(nursesDTO, HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.POST, value = "/addnurse")
	@PreAuthorize("hasAuthority('CLINICADMIN')")
	public ResponseEntity<NurseDTO> addNurse(@RequestBody NurseDTO nurseDTO) {

		nurseService.save(nurseDTO);
		return new ResponseEntity<>(nurseDTO, HttpStatus.CREATED);

	}
}

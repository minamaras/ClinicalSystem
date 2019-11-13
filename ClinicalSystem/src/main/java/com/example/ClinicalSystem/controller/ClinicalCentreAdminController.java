package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.RequiredAnnotationBeanPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.service.ClinicalCentreAdminService;

@RestController
@RequestMapping(value = "api/clinicalcentreadmins")
public class ClinicalCentreAdminController {

	@Autowired
	private ClinicalCentreAdminService ccaService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ClinicalCentreAdminDTO>> getAllccAdmins(){
		List<ClinicalCentreAdmin> ccAdmins = ccaService.findAll();
		
		List<ClinicalCentreAdminDTO> ccAdminsDTO = new ArrayList<>();
		for(ClinicalCentreAdmin cca : ccAdmins) {
			ccAdminsDTO.add(new ClinicalCentreAdminDTO(cca));
		}
		return new ResponseEntity<>(ccAdminsDTO, HttpStatus.OK);
		
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<ClinicalCentreAdminDTO> saveccAdmin(@RequestBody ClinicalCentreAdminDTO ccAdminDTO){
		
		ClinicalCentreAdmin ccAdmin = new ClinicalCentreAdmin();
		ccAdmin.setId(ccAdminDTO.getId());
		ccAdmin.setName(ccAdminDTO.getFirstName());
		ccAdmin.setLastname(ccAdminDTO.getLastName());
		ccAdmin.setEmail(ccAdminDTO.getEmail());
		
		ccAdmin = ccaService.save(ccAdmin);
		return new ResponseEntity<>(new ClinicalCentreAdminDTO(ccAdmin), HttpStatus.CREATED);
	}
	
	/*
	@RequestMapping(method = RequestMethod.POST, value = "/addccadmin")
	public void addccAdmin(@RequestBody ClinicalCentreAdminDTO ccAdminDTO) {
		
		
		
	}
	
	*/
}

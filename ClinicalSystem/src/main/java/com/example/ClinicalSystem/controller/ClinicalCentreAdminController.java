package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.ClinicAdminService;
import com.example.ClinicalSystem.service.ClinicalCentreAdminService;
import com.example.ClinicalSystem.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/clinicalcentreadmins")
public class ClinicalCentreAdminController {

	@Autowired
	private ClinicalCentreAdminService ccaService;
	private ClinicAdminService clinicAdminService;
	private UserService userService;
	
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
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/addccadmin")
	public ResponseEntity<ClinicalCentreAdmin> addccAdmin(@RequestBody ClinicalCentreAdminDTO ccAdminDTO) {
		
		User u = userService.findByEmail(ccAdminDTO.getEmail());
		if (u != null) {
			return null;
		}
		
		ClinicalCentreAdmin ccAdmin = new ClinicalCentreAdmin();
		ccAdmin.setEmail(ccAdminDTO.getEmail());
		ccAdmin.setName(ccAdminDTO.getFirstName());
		ccAdmin.setLastname(ccAdminDTO.getLastName());
		ccAdmin.setPassword(ccAdminDTO.getPassword());
		
		ccaService.save(ccAdmin);
	 return new ResponseEntity<>(ccAdmin,HttpStatus.CREATED);
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/addclinicadmin")
	public ResponseEntity<ClinicAdmin> addClinicAdmin(@RequestBody ClinicAdminDTO clinicAdminDTO) {
		
		User u = userService.findByEmail(clinicAdminDTO.getEmail());
		if (u != null) {
			return null;
		}
		
		ClinicAdmin clinicAdmin = new ClinicAdmin();
		clinicAdmin.setEmail(clinicAdminDTO.getEmail());
		clinicAdmin.setName(clinicAdminDTO.getFirstName());
		clinicAdmin.setLastname(clinicAdmin.getLastname());
		clinicAdmin.setPassword(clinicAdminDTO.getPassword());
		
		clinicAdminService.save(clinicAdmin);
	 return new ResponseEntity<>(clinicAdmin,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addclinic")
	public ResponseEntity<Clinic> addClinic(@RequestBody Clinic clinic) {
		
		Clinic c = new Clinic();
		c.setName(clinic.getName());
		c.setAdress(clinic.getAdress());
		c.setDescription(clinic.getDescription());
		c.setFreeAppointment(clinic.getFreeAppointment());
		c.setPrice(clinic.getPrice());
		
		ccaService.addClinic(c);
	 return new ResponseEntity<>(c,HttpStatus.CREATED);
		
	}
	
}

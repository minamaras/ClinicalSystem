package com.example.ClinicalSystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
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

	@GetMapping(value = "/all")
	public ResponseEntity<List<ClinicalCentreAdminDTO>> getAllccAdmins(){
		List<ClinicalCentreAdmin> ccAdmins = ccaService.findAll();

		List<ClinicalCentreAdminDTO> ccAdminsDTO = new ArrayList<>();
		for(ClinicalCentreAdmin cca : ccAdmins) {
			ccAdminsDTO.add(new ClinicalCentreAdminDTO(cca));
		}
		return new ResponseEntity<>(ccAdminsDTO, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/addccadmin")
	public ResponseEntity<ClinicalCentreAdminDTO> addccAdmin(@RequestBody ClinicalCentreAdminDTO ccAdminDTO) {
		ccaService.save(ccAdminDTO);
		return new ResponseEntity<>(ccAdminDTO,HttpStatus.CREATED);

	}


	@RequestMapping(method = RequestMethod.POST, value = "/addclinic")
	public ResponseEntity<Clinic> addClinic(@RequestBody ClinicDTO clinicDTO) {

		Clinic c = new Clinic();
		c.setName(clinicDTO.getName());
		c.setAdress(clinicDTO.getAdress());
		c.setDescription(clinicDTO.getDescription());
		//c.setFreeAppointment(clinic.getFreeAppointment());
		//c.setPrice(clinic.getPrice());

		ccaService.addClinic(c);
	 return new ResponseEntity<>(c,HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/allclinics")
	public ResponseEntity<List<ClinicDTO>> getAllClinics() {

		List<Clinic> clinics = ccaService.findAllClinics();

		// convert students to DTOs
		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for (Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}

		return new ResponseEntity<>(clinicsDTO, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/allclinicadmins")
	public ResponseEntity<List<ClinicAdminDTO>> getAllClinicAdmins() {

		List<ClinicAdmin> clinicAdmins = clinicAdminService.findAll();

		// convert students to DTOs
		List<ClinicAdminDTO> clinicAdminsDTO = new ArrayList<>();
		for (ClinicAdmin ca : clinicAdmins) {
			clinicAdminsDTO.add(new ClinicAdminDTO(ca));
		}

		return new ResponseEntity<>(clinicAdminsDTO, HttpStatus.OK);
	}

}

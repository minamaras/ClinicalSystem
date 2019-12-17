package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.PatientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.service.PatientService;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;
    @Autowired
	private ModelMapper modelMapper;

	@RequestMapping(method= RequestMethod.GET, value="/confirm-account/{verificationCode}")
	public ResponseEntity confirmUserAccount(@PathVariable("verificationCode") String verificationCode) throws URISyntaxException {
		//ConfirmationToken token = confirmationTokenService.findByConfirmationToken(verificationCode);
		Patient patient = patientService.findVerificationCode(verificationCode);
		patient.setActive(true);
		patientService.updatePatient(patient);

		URI newUri = new URI("http://localhost:3000/activated-account");
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(newUri);

		return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
	}



	@RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
	@PreAuthorize("hasAuthority('PATIENT')")
	public ResponseEntity<PatientDTO> updateProfile(@RequestBody PatientDTO patientDTO) {

		if(patientService.findPatient(patientDTO.getEmail()) == null){
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}else {

			Patient patient = patientService.findPatient(patientDTO.getEmail());

			if (patientDTO.getName() != "") {

				patient.setName(patientDTO.getName());
			}

			if (patientDTO.getLastname() != "") {
				patient.setLastname(patientDTO.getLastname());
			}

			if (patientDTO.getAdress() != "") {
				patient.setAdress(patientDTO.getAdress());
			}

			if (patientDTO.getCountry() != "") {
				patient.setCountry(patientDTO.getCountry());
			}

			if (patientDTO.getCity() != "") {
				patient.setCity(patientDTO.getCity());
			}

			if (patientDTO.getPhone() != "") {
				patient.setPhone(patientDTO.getPhone());
			}

			patientService.updatePatient(patient);
			return new ResponseEntity<>(modelMapper.map(patient,PatientDTO.class),HttpStatus.OK);
		}

	}



}

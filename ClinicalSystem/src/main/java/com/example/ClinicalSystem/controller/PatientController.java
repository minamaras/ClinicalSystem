package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.model.Authority;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.service.PatientService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;
    @Autowired
	private ModelMapper modelMapper;


	@RequestMapping(method= RequestMethod.GET, value="/confirm-account/{verificationCode}")
	public ResponseEntity<?> confirmUserAccount(@PathVariable("verificationCode") String verificationCode)
	{
		//ConfirmationToken token = confirmationTokenService.findByConfirmationToken(verificationCode);
		Patient patient = patientService.findVerificationCode(verificationCode);

		if(patient.getVerificationCode() != null)
		{
			try {
				//Patient p = patientService.findPatient(token.getPatient().getEmail());
				patient.setActive(true);
				patientService.updatePatient(patient);

				return new ResponseEntity<>("User "+ patient.getName() +" is successfully registered!", HttpStatus.OK);

			} catch (Exception e){
				return new ResponseEntity<>("Account activation is unsuccessful.", HttpStatus.NOT_FOUND);
			}

		}
		else
		{
			return new ResponseEntity<>("The link is invalid.", HttpStatus.NOT_FOUND);
		}

	}

  
  @RequestMapping(method = RequestMethod.POST, value = "/updateprofile")
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

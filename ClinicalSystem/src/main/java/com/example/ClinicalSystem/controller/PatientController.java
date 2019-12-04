package com.example.ClinicalSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.service.PatientService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

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
				patientService.savePatient(patient);

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


}

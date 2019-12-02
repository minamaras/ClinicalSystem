package com.example.ClinicalSystem.repository;


import com.example.ClinicalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.PatientRequest;



@Repository
public interface PatientRequestRepository extends JpaRepository<PatientRequest, Long>  {
	
	
	PatientRequest findByEmail(String email);	
	PatientRequest save(PatientRequest patientRequest);
	Long removeByEmail(String email);
	

}
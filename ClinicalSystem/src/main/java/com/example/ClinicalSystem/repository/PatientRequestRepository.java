package com.example.ClinicalSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.PatientRequest;



@Repository
public interface PatientRequestRepository extends JpaRepository<PatientRequest, Long>  {
	
	
	PatientRequest findByEmail(String email);	
	PatientRequest save(PatientRequest patientRequest);
	

}
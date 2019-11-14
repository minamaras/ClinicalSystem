package com.example.ClinicalSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>  {
	
	
	Patient findByEmail(String email);	
	Patient save(Patient patient);

}

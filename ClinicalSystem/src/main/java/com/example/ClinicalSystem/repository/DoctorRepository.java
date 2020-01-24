package com.example.ClinicalSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.Doctor;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	Doctor save(Doctor doctor);

	Doctor findByEmail(String email);

	void deleteByEmail(String email);



}

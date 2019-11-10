package com.example.ClinicalSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ClinicalSystem.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	Doctor save(Doctor doctor);

}

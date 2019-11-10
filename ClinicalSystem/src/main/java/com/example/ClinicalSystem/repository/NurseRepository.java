package com.example.ClinicalSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ClinicalSystem.model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

	
	Nurse save(Nurse nurse);
}

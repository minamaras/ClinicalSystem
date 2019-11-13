package com.example.ClinicalSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.ClinicalCentreAdmin;

@Repository
public interface ClinicalCentreAdminRepository extends JpaRepository<ClinicalCentreAdmin, Long> {
	
	ClinicalCentreAdmin save(ClinicalCentreAdmin clinicalCentreAdmin);
	ClinicalCentreAdmin findByEmail(String email);
}

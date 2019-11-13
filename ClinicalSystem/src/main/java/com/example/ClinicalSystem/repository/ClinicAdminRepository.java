package com.example.ClinicalSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.ClinicAdmin;

@Repository
public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long>{


	ClinicAdmin findByEmail(String email);
	ClinicAdmin save(ClinicAdmin clinicAdmin);

}

package com.example.ClinicalSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ClinicalSystem.model.ClinicAdmin;

public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long>{


	ClinicAdmin findByEmail(String email);

}

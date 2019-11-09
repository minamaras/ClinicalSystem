package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.ClinicalCentreAdmin;

public interface ClinicalCentreAdminRepository extends JpaRepository<ClinicalCentreAdmin, Long> {
	
	ClinicalCentreAdmin save(ClinicalCentreAdmin clinicalCentreAdmin);
	ClinicalCentreAdmin findByEmail(String email);
}

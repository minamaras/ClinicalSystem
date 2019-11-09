package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.ClinicAdmin;

public interface ClinicAdminRepository extends JpaRepository<ClinicAdmin, Long>{
	
	
	ClinicAdmin findByEmail(String email);

}
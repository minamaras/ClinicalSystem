package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Patient;
import model.User;

public interface PatientRepository extends JpaRepository<Patient, Long>  {
	
	
	Patient findByUsernameIgnoreCase(String username);
	Patient findByEmail(String email);	
	Patient save(Patient patient);

}

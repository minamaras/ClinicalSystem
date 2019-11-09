package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	
	Doctor save(Doctor doctor);

}

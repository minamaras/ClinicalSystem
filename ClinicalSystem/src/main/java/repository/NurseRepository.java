package repository;


import org.springframework.data.jpa.repository.JpaRepository;

import model.Nurse;

public interface NurseRepository extends JpaRepository<Nurse, Long> {

	
	Nurse save(Nurse nurse);
}

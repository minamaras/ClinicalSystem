package com.example.ClinicalSystem.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	
	Optional<User> findById(Long id);
	User findByEmail(String email);
	User save(User user);
	User findByPassword(String password);
	
	
	
}

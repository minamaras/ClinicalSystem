package com.example.ClinicalSystem.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>  {

	
	User findByUsernameIgnoreCase(String username);
	Optional<User> findById(Long id);
	User findByEmail(String email);
	User save(User user);
	User findByPassword(String password);
	
	
	
}

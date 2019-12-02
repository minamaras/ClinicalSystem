package com.example.ClinicalSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

	
	Optional<User> findById(Long id);
	User findByEmail(String email);
	User save(User user);
	User findByPassword(String password);
	List<User> findAll();
	
	
	
}

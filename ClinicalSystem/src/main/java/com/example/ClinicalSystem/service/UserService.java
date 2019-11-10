package com.example.ClinicalSystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.repository.UserRepository;

public class UserService {
	
		
	@Autowired
	private UserRepository userRepository;
	
	
	public User findByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}
	
	public User findByUsername(String username) {
		
		return userRepository.findByUsernameIgnoreCase(username);
	}
	
	public User save(User user) {
		
		return userRepository.save(user);
	}
	
	public User findbyPassword(String password) {
		
		return userRepository.findByPassword(password);
		
	}

}

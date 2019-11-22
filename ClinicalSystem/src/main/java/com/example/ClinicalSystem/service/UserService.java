package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.repository.UserRepository;

@Service
public class UserService {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;


	public boolean loginUser(UserDTO user) {

		User u = modelMapper.map(user, User.class);
		boolean isFound = false;

		if (userRepository.findByEmail(u.getEmail()) == null) {

			isFound = false;
		} else if (userRepository.findByEmail(u.getEmail()) == userRepository.findByPassword(u.getPassword())) {

			isFound = true;

		}

		return isFound;
	}


	public boolean existsInDB(UserDTO userDTO) {
		
		User user = modelMapper.map(userDTO,User.class);
		
		User u = userRepository.findByEmail(user.getEmail());
		boolean exists = false;

		if (u != null) {

			exists = true;
		}

		return exists;
	}

}
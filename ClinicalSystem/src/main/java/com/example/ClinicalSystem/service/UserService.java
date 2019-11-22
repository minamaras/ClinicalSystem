package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.UserDTO;
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
	
	
	public ResponseEntity<UserDTO> loginUser(UserDTO user) {

		User u = modelMapper.map(user,User.class);
		boolean pronadjen = false;

		if(userRepository.findByEmail(u.getEmail()) == null){

			pronadjen = false;
		}

		else if (userRepository.findByEmail(u.getEmail()) ==  userRepository.findByPassword(u.getPassword())){

			pronadjen = true;

		}

		if(pronadjen){

			return  new ResponseEntity<UserDTO>(HttpStatus.OK);
		}else {

			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}


	}
	

	
	public User save(User user) {
		
		return userRepository.save(user);
	}
	
	public User findbyPassword(String password) {
		
		return userRepository.findByPassword(password);
		
	}

}

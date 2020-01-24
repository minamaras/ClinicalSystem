package com.example.ClinicalSystem.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import com.example.ClinicalSystem.DTO.ChangePasswordDTO;
import com.example.ClinicalSystem.model.Role;
import com.example.ClinicalSystem.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.service.PatientService;
import com.example.ClinicalSystem.service.UserService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

    @Autowired
    ModelMapper modelMapper;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {

		boolean isloggedin = userService.loginUser(userDTO);

		if(isloggedin) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getrole/{email:.+}")
	public ResponseEntity<?> getRole(@PathVariable String email) {

		User user = userService.findByUsername(email);
		Role role = user.getRole();

		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/changepass")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {

	boolean isChanged = userService.changePassword(changePasswordDTO);

	if(isChanged) {
		return new ResponseEntity<>(HttpStatus.OK);
	}else {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	}

}

package com.example.ClinicalSystem.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public User login(@RequestBody UserDTO user) {
		
		User u = userService.findByEmail(user.getEmail());
		if(u == null) {
			
			return null;
			
		}
		
		boolean postoji = false;
		User uPass = userService.findbyPassword(user.getPassword());
		if( u == uPass) {
			
			postoji = true;
		}
		return u;
	}
	
		
	 /*@RequestMapping(method = RequestMethod.GET, value = "/login")
	    public void redirect(HttpServletResponse response) throws IOException{
	        response.sendRedirect("http://localhost:8081/login.html");
	    }*/

}

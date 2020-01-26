package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ChangePasswordDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.ClinicalCentreAdminRepository;
import com.example.ClinicalSystem.service.interfaces.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.repository.UserRepository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserServiceInterface, UserDetailsService {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClinicalCentreAdminRepository clinicalCentreAdminRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User findByUsername(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}


	public boolean loginUser(UserDTO user) {

		User u = modelMapper.map(user, User.class);
		boolean isFound = false;

		if (userRepository.findByEmail(u.getEmail()) == null) {

			isFound = false;
			
		} else if (userRepository.findByEmail(u.getEmail()) == userRepository.findByPassword(u.getPassword())) {

			if(user.getRole() == Role.PATIENT) {
				
					Patient p = patientService.findPatient(user.getEmail());
					
					if(p.isActive()) {isFound = true;} 
					else {isFound = false;}		
			}
			
		  else {isFound = true;}
	
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



	public User findByEmail(UserDTO userDTO) {

		User user = modelMapper.map(userDTO,User.class);

		User u = userRepository.findByEmail(user.getEmail());
		boolean exists = false;

		if (u != null) {

			return  u;
		}

		return null;
	}

	public boolean changePassword(ChangePasswordDTO changePasswordDTO){

		User user = findByUsername(changePasswordDTO.getEmail());
		final Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), changePasswordDTO.getOldpassword()));
		if(authentication == null){
			return false;
		} else {
			user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewpassword()));

			if(user.getRole().equals(Role.CLINICALCENTREADMIN)){
				ClinicalCentreAdmin cca = (ClinicalCentreAdmin) user;
				cca.setFirstLogin(false);
				clinicalCentreAdminRepository.save(cca);
			}

			userRepository.save(user);
			return true;
		}

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = (userRepository.findByEmail(email));

		if(user == null)
			throw new UsernameNotFoundException("User with "+ email+" doesn't exists!");



		return user;
	}

	@Transactional
	public void saveHoliday(User user, Holiday holiday) {
		Set<Holiday> holidays = user.getHolidays();
		holidays.add(holiday);

		user.setHolidays(holidays);
		userRepository.save(user);
	}
}
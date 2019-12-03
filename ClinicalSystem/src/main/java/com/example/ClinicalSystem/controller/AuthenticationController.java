package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ClinicalCentreAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.PatientDTO;
import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.security.TokenUtils;
import com.example.ClinicalSystem.security.auth.JwtAuthenticationRequest;
import com.example.ClinicalSystem.security.auth.TokenAuthenticationFilter;
import com.example.ClinicalSystem.service.*;
import com.example.ClinicalSystem.service.interfaces.PatientRequestServiceInterface;
import com.example.ClinicalSystem.service.interfaces.UserServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.ServletRequest;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private PatientRequestService patientRequestService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClinicalCentreAdminService clinicalCentreAdminService;


    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<?> register(@RequestBody PatientDTO patientDTO) {

        boolean registered = patientService.register(patientDTO);

        if(registered){

            return new ResponseEntity<>(patientDTO, HttpStatus.CREATED);
        }
        else {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest authenticationRequest) {

        final Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());


        //username = email in this case
        String jwt = tokenUtils.generateToken(details.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }




//    @RequestMapping(method = RequestMethod.GET, value = "/user")
//    public ResponseEntity<?> getCurrentUser(@RequestHeader(value="token") String token) {
//
//        String email = tokenUtils.getEmailFromToken(token);
//
//        User user = userService.findByUsername(email);
//
//        if(user !=  null) {
//            UserDTO userDto = modelMapper.map(user, UserDTO.class);
//            return new ResponseEntity<>(userDto, HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole() == Role.PATIENT) {
            Patient p = patientService.findPatient(user.getEmail());
            PatientDTO patientDTO = modelMapper.map(p, PatientDTO.class);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);

        }else if ( user.getRole() == Role.CLINICALCENTREADMIN){

            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            User cca = userService.findByEmail(userDTO);
            UserDTO ccaDTO = modelMapper.map(cca,UserDTO.class);
            return new ResponseEntity<>(ccaDTO, HttpStatus.OK);
        }else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        }

    }





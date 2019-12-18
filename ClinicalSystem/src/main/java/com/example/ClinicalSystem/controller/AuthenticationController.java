package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.*;
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
    private ClinicAdminService clinicAdminService;

    @Autowired
    private NurseService nurseService;

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
        if(authentication == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        if(details.getAuthorities().contains("PATIENT")) {
            String email = authenticationRequest.getEmail();
            if (patientService.findPatient(email).isActive() == false) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        //username = email in this case
        String jwt = tokenUtils.generateToken(details.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }



    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) a.getPrincipal();

        if(user.getRole() == Role.PATIENT) {
            Patient p = patientService.findPatient(user.getEmail());
            PatientDTO patientDTO = modelMapper.map(p, PatientDTO.class);
            return new ResponseEntity<>(patientDTO, HttpStatus.OK);

        }else if ( user.getRole() == Role.CLINICALCENTREADMIN){

            ClinicalCentreAdmin cca = clinicalCentreAdminService.findByEmail(user.getEmail());
            ClinicalCentreAdminDTO ccaDTO = modelMapper.map(cca,ClinicalCentreAdminDTO.class);
            return new ResponseEntity<>(ccaDTO, HttpStatus.OK);

        }else if ( user.getRole() == Role.DOCTOR){

            Doctor d = doctorService.findOne(user.getEmail());
            DoctorDTO doctorDTO = modelMapper.map(d, DoctorDTO.class);
            return new ResponseEntity<>(doctorDTO, HttpStatus.OK);

        } else if ( user.getRole() == Role.CLINICADMIN){

            ClinicAdmin clinicAdmin = clinicAdminService.findByEmail(user.getEmail());
            ClinicAdminDTO clinicAdminDTO = modelMapper.map(clinicAdmin, ClinicAdminDTO.class);
            return new ResponseEntity<>(clinicAdminDTO, HttpStatus.OK);

        } else if ( user.getRole() == Role.NURSE){

            Nurse nurse = nurseService.findByEmail(user.getEmail());
            NurseDTO nurseDTO = modelMapper.map(nurse, NurseDTO.class);
            return new ResponseEntity<>(nurseDTO, HttpStatus.OK);
        }


        else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        }

    }





package com.example.ClinicalSystem.service;


import java.security.Principal;
import java.util.*;

import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.repository.NurseRepository;

import javax.transaction.Transactional;

@Service
public class NurseService {

	@Autowired
	private NurseRepository nurseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DoctorService doctorService;

	@Transactional
	public List<NurseDTO> findAll(){
		List<Nurse> nurses = nurseRepository.findAll();

		List<NurseDTO> nursesDTO = new ArrayList<>();
		for (Nurse n : nurses) {
			nursesDTO.add(new NurseDTO(n));
		}

		return nursesDTO;
	
	}

	@Transactional
	public boolean save(NurseDTO nurseDTO, Principal p) {

		if(userService.findByUsername(nurseDTO.getEmail()) != null) {
			return false;
		}

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinic = cAdmin.getClinic();

		Nurse nurse = modelMapper.map(nurseDTO, Nurse.class);

		if(clinic != null) {
			nurse.setClinic(clinic);
			clinic.getNurses().add(nurse);
		}

		nurse.setPassword(passwordEncoder.encode(nurse.getPassword()));

		Authority authoritie = authorityService.findByname("NURSE");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		nurse.setAuthorities(authorities);
		nurseRepository.save(nurse);
		return true;
	}

	@Transactional
	public Nurse findByEmail(String email){

		Nurse nurse = nurseRepository.findByEmail(email);
		return  nurse;

	}

	public Nurse updateNurse(Nurse nurse) {
		return nurseRepository.save(nurse);
	}

	public NurseCalendarDTO getAllAppoints(Principal p){
		Nurse nurse = (Nurse) userService.findByUsername(p.getName());

		String clinicName = nurse.getClinic().getName();
		Set<DoctorDTO> doctors = doctorService.findAllDoctorsFromAClinic(clinicName);

		NurseCalendarDTO nurseCalendarDTO = new NurseCalendarDTO(nurse);

		List<AppointmentDTO> appoints = new ArrayList<>();
		for (DoctorDTO doc: doctors){
			for(AppointmentDTO appDTO : doc.getAppointments()){
				appoints.add(appDTO);
			}
		}

		Set<HolidayDTO> holidayDTOS = new HashSet<>();
		for ( Holiday h : nurse.getHolidays()){

			HolidayDTO holidayDTO = modelMapper.map(h,HolidayDTO.class);
			holidayDTO.setFromto(h.getStart().toString()+"-"+h.getEnd().toString());
			holidayDTO.setStartHoliday(h.getStart().toString().substring(0,10));
			holidayDTO.setEndHoliday(h.getEnd().toString().substring(0,10));
			holidayDTOS.add(holidayDTO);
		}

		nurseCalendarDTO.setAppointments(appoints);
		nurseCalendarDTO.setHolidays(holidayDTOS);

		return nurseCalendarDTO;
	}
	
}

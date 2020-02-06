package com.example.ClinicalSystem.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.*;

import java.util.*;

import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.repository.DoctorRepository;

import javax.print.Doc;
import javax.transaction.Transactional;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private ClinicService clinicService;

	@Autowired
	private ExamTypeService examTypeService;

	@Autowired
	private PatientService patientService;


	public Set<DoctorDTO> findAll(Principal p) {

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinic = cAdmin.getClinic();

		Set<Doctor> doctors = clinic.getDoctors();

		Set<DoctorDTO> doctorsDTO = new HashSet<>();
		for (Doctor d : doctors) {

			DoctorDTO doctorDTO = modelMapper.map(d,DoctorDTO.class);
			doctorDTO.setExamType(modelMapper.map(d.getExamType(),ExamTypeDTO.class));

			List<AppointmentDTO> lista = new ArrayList<>();
			Set<HolidayDTO> holidayDTOS = new HashSet<>();

			for(Appointment a : d.getAppointments()){
				AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
				appointmentDTO.setDate(a.getStart().toString().substring(0,10));
				appointmentDTO.setStartTime(a.getStartTime());
				appointmentDTO.setEndTime(a.getEndTime());
				lista.add(appointmentDTO);

			}

			for ( Holiday h : d.getHolidays()){

				HolidayDTO holidayDTO = modelMapper.map(h,HolidayDTO.class);
				holidayDTO.setFromto(h.getStart().toString()+"-"+h.getEnd().toString());
				holidayDTOS.add(holidayDTO);
			}


			doctorDTO.setStart(d.getStart());
			doctorDTO.setEnd(d.getEnd());
			doctorDTO.setAppointments(lista);
			doctorDTO.setHolidays(holidayDTOS);
			doctorsDTO.add(doctorDTO);
		}

		return doctorsDTO;
	}

	public Doctor updateDoctor(Doctor doctor)
	{
		return doctorRepository.save(doctor);
	}

	public Doctor save(DoctorDTO doctorDTO, Principal p) {

		ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
		Clinic clinic = cAdmin.getClinic();


		Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);

		ExamType  examType = examTypeService.findOne(doctorDTO.getExamType().getName());
		doctor.setExamType(examType);

		if(doctor.getStart().compareTo(doctor.getEnd()) > 0 || doctor.getStart().compareTo(doctor.getEnd()) == 0 ) {
			return null;
		}

		if(clinic != null) {
			doctor.setClinic(clinic);
			clinic.getDoctors().add(doctor);
		}

		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

		Authority authoritie = authorityService.findByname("DOCTOR");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		doctor.setAuthorities(authorities);

		return doctorRepository.save(doctor);
	}

    @Transactional
    public boolean removeDoctor(DoctorDTO doctorDto) {
		UserDTO userDto = modelMapper.map(doctorDto, UserDTO.class);

		if(userService.existsInDB(userDto)) {
			Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

			if(doctor.getAppointments().size() >= 1) {
				return  false;
			}

			doctorRepository.deleteByEmail(doctor.getEmail());

			return true;
		}

		return false;

	}

	public Doctor findOne(String email) {
		return doctorRepository.findByEmail(email);
	}

	public Doctor findOneById(Long id) {

		Optional<User> user = userService.findById(id);
		User u = user.get();
		Doctor doctor = doctorRepository.findByEmail(u.getEmail());

		return doctor;
	}


	public Set<DoctorDTO> findAllDoctorsFromAClinic(String clinicname){

		HashSet<DoctorDTO> doctorsret = new HashSet<>();
		String cleanText = clinicname.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
		Clinic clinic = clinicService.findName(cleanText);

		Set<Doctor> docs =clinic.getDoctors();
		Set<Long> setOfIds = new HashSet<>();
		for(Doctor doctor : docs){
			setOfIds.add(doctor.getId());
			DoctorDTO doctorDTO = modelMapper.map(doctor,DoctorDTO.class);
			ExamTypeDTO examTypeDTO = modelMapper.map(doctor.getExamType(),ExamTypeDTO.class);
			doctorDTO.setClinicid(clinic.getId());
			doctorDTO.setExamType(examTypeDTO);


			List<AppointmentDTO> lista = new ArrayList<>();
			Set<HolidayDTO> holidayDTOS = new HashSet<>();
			List<String> patients = new ArrayList<>();

			for(Appointment a : doctor.getAppointments()){
				AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
				appointmentDTO.setDate(a.getStart().toString().substring(0,10));
				appointmentDTO.setStartTime(a.getStartTime());
				appointmentDTO.setEndTime(a.getEndTime());

				if(a.getStatus().equals(AppointmentStatus.HAS_HAPPEND) && a.getClassification().equals(AppointmentClassification.NORMAL)){
					patients.add(a.getPatient().getEmail());
				}
				lista.add(appointmentDTO);

			}

			for ( Holiday h : doctor.getHolidays()){

				HolidayDTO holidayDTO = modelMapper.map(h,HolidayDTO.class);
				holidayDTO.setFromto(h.getStart().toString()+"-"+h.getEnd().toString());
				holidayDTOS.add(holidayDTO);
			}

			if(doctor.getSingleratings().size() == 0){
				doctorDTO.setRating(0);
			}else{

				double suma=0;

			for(Rating r : doctor.getSingleratings()){
				suma = suma + r.getValue();
			}
			double rating = suma/(doctor.getSingleratings().size());
			doctorDTO.setRating(rating);

			}

			lista.sort(Comparator.comparing(AppointmentDTO::getStart));

			doctorDTO.setAppointments(lista);
			doctorDTO.setHolidays(holidayDTOS);
			doctorDTO.setPatients(patients);
			doctorsret.add(doctorDTO);

		}

		return  doctorsret;
	}


	public DoctorDTO updatedrating(String email,int rating){

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		Patient p = patientService.findPatient(user.getEmail());

		Doctor doctor = doctorRepository.findByEmail(email);
		doctor.addPatientThatRated(p);
		p.addRatedDoctor(doctor);

		Rating dodatrejting = new Rating();
		dodatrejting.setValue(rating);

		ratingService.save(dodatrejting);

		doctor.addNewSingleRating(dodatrejting);

		double suma = 0;
		for(Rating r : doctor.getSingleratings()){
			suma = suma + r.getValue();
		}

		double novirejting = suma/(doctor.getSingleratings().size());

		//doctor.setRating(novirejting);

		if(doctorRepository.save(doctor) != null){
			return modelMapper.map(doctor,DoctorDTO.class);
		}else{
			return  null;
		}
	}

	public List<Doctor> findAllDoctors() {

		List<Doctor> doctors = doctorRepository.findAll();

		return doctors;
	}

	public DoctorDTO findOneByPrincipal(Principal p){
		Doctor doctor = (Doctor) userService.findByUsername(p.getName());

		List<AppointmentDTO> lista = new ArrayList<>();
		Set<HolidayDTO> holidayDTOS = new HashSet<>();

		for(Appointment a : doctor.getAppointments()){
			AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
			appointmentDTO.setDate(a.getStart().toString().substring(0,10));
			appointmentDTO.setStartTime(a.getStartTime());
			appointmentDTO.setEndTime(a.getEndTime());
			if (a.getPatient() != null) {
				appointmentDTO.setPatientemail(a.getPatient().getName());
				appointmentDTO.setPatientName(a.getPatient().getName());
				appointmentDTO.setPatientLastname(a.getPatient().getLastname());
			}
			lista.add(appointmentDTO);
		}

		for ( Holiday h : doctor.getHolidays()){

			HolidayDTO holidayDTO = modelMapper.map(h,HolidayDTO.class);
			holidayDTO.setFromto(h.getStart().toString()+"-"+h.getEnd().toString());
			holidayDTO.setStartHoliday(h.getStart().toString().substring(0,10));
			holidayDTO.setEndHoliday(h.getEnd().toString().substring(0,10));
			holidayDTOS.add(holidayDTO);
		}
		lista.sort(Comparator.comparing(AppointmentDTO::getStart));

		DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
		doctorDTO.setAppointments(lista);
		doctorDTO.setHolidays(holidayDTOS);

		return doctorDTO;
	}



}

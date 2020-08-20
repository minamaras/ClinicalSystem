package com.example.ClinicalSystem.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.ClinicalSystem.DTO.*;

import java.util.*;

import com.example.ClinicalSystem.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ClinicalSystem.repository.DoctorRepository;

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
  	@Autowired
	private OperationRequestService operationRequestService;

	public Set<DoctorDTO> findAllDoctorsFromClinic(Principal principal) {
		ClinicAdmin currentClinicAdmin = getLoggedInClinicAdmin(principal.getName());

		Set<DoctorDTO> doctorsFromClinicToReturn = new HashSet<>();
		for (Doctor doctor : currentClinicAdmin.getClinic().getDoctors()) {
			DoctorDTO setUpDoctorDto = setUpDoctorDtoFromDoctor(doctor);
			doctorsFromClinicToReturn.add(setUpDoctorDto);
		}

		return doctorsFromClinicToReturn;
	}

	public ClinicAdmin getLoggedInClinicAdmin(String username) {
		return (ClinicAdmin) userService.findByUsername(username);
	}

	public DoctorDTO setUpDoctorDtoFromDoctor(Doctor doctor) {
		DoctorDTO doctorDto= modelMapper.map(doctor,DoctorDTO.class);
		doctorDto.setExamType(modelMapper.map(doctor.getExamType(),ExamTypeDTO.class));

		doctorDto.setAppointments(linkAppointmentsWithDoctor(doctor));
		doctorDto.setHolidays(linkHolidayWithDoctor(doctor));

		return doctorDto;
	}

	public ArrayList<AppointmentDTO> linkAppointmentsWithDoctor(Doctor doctor) {
		ArrayList<AppointmentDTO> allDoctorsAppointmentsDto = new ArrayList<>();
		for (Appointment appointment : doctor.getAppointments()) {
			AppointmentDTO appointmentDto = modelMapper.map(appointment, AppointmentDTO.class);
			allDoctorsAppointmentsDto.add(appointmentDto);
		}

		return allDoctorsAppointmentsDto;
	}

	public ArrayList<HolidayDTO> linkHolidayWithDoctor(Doctor doctor) {
		ArrayList<HolidayDTO> doctorsHolidayDto = new ArrayList<>();
		for (Holiday holiday : doctor.getHolidays()) {
			HolidayDTO holidayDto= modelMapper.map(holiday, HolidayDTO.class);
			doctorsHolidayDto.add(holidayDto);
		}

		return doctorsHolidayDto;
	}

	public void updateDoctor(Doctor doctor)
	{
		doctorRepository.save(doctor);
	}

	public Doctor save(DoctorDTO doctorDto, Principal p) {
		Clinic clinic = getClinicFromLoggedInClinicAdmin(p);
		Doctor doctor = modelMapper.map(doctorDto, Doctor.class);

		ExamType examTypeThatDoctorProvides = examTypeService.findExamTypeByItsName(doctorDto.getExamType().getName());
		doctor.setExamType(examTypeThatDoctorProvides);

		setUpNewDoctor(doctor);
		doctor.addToClinic(clinic);

		return doctorRepository.save(doctor);
	}

	private void setUpNewDoctor(Doctor doctor) {
		setExamTypeForDoctor(doctor);
		doctor.checkDoctorWorkingHours();
		encodePasswordForNewDoctor(doctor);
		assignAuthorityForDoctor(doctor);
	}

	private void setExamTypeForDoctor(Doctor doctor) {
		ExamType examTypeThatDoctorProvides = examTypeService
				.findExamTypeByItsName(modelMapper.map(doctor, DoctorDTO.class)
						.getExamType()
						.getName());

		doctor.setExamType(examTypeThatDoctorProvides);
	}

	private Clinic getClinicFromLoggedInClinicAdmin(Principal p) {
		ClinicAdmin clinicAdmin = getLoggedInClinicAdmin(p.getName());
		return clinicAdmin.getClinic();
	}

	private void encodePasswordForNewDoctor(Doctor doctor) {
		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
	}

	private void assignAuthorityForDoctor(Doctor doctor) {
		List<Authority> authorities = new ArrayList<>();
		Authority authority = authorityService.findByname(doctor.getRole().toString());

		authorities.add(authority);
		doctor.setAuthorities(authorities);
	}

	@Transactional
    public boolean removeDoctor(Doctor doctor) {
		if (!doctor.canBeRemoved()) return false;

		doctor.removeFromClinic();
		doctorRepository.delete(doctor);
		return true;
	}

	public Doctor findDoctorByEmail(String email) {
		return doctorRepository.findByEmail(email);
	}

	public Doctor findDoctorById(Long id) {
		return doctorRepository.findByEmail(userService.findById(id).get().getEmail());
	}
	
	public Set<DoctorDTO> findAllDoctorsFromAClinic(String clinicname){
		HashSet<DoctorDTO> doctorsret = new HashSet<>();
		String cleanText = clinicname.replaceAll("\\d+", "").replaceAll("(.)([A-Z])", "$1 $2");
		Clinic clinic = clinicService.findName(cleanText);

		Set<Doctor> docs =clinic.getDoctors();
		Set<Long> setOfIds = new HashSet<>();

		for(Doctor doctor : docs) {
			setOfIds.add(doctor.getId());
			DoctorDTO doctorDTO = new DoctorDTO();
			doctorDTO.setName(doctor.getName());
			doctorDTO.setLastname(doctor.getLastname());
			doctorDTO.setStart(doctor.getWorkingHours().getStart());
			doctorDTO.setEnd(doctor.getWorkingHours().getEnd());
			doctorDTO.setId(doctor.getId());
			doctorDTO.setEmail(doctor.getEmail());
			doctorDTO.setSpecialization(doctor.getSpecialization());
			doctorDTO.setClinicname(doctor.getClinic().getName());
			ExamTypeDTO examTypeDTO = modelMapper.map(doctor.getExamType(),ExamTypeDTO.class);
			doctorDTO.setClinicid(clinic.getId());
			doctorDTO.setExamType(examTypeDTO);

			List<AppointmentDTO> lista = new ArrayList<>();
			List<HolidayDTO> holidayDTOS = new ArrayList<>();
			List<String> patients = new ArrayList<>();

			for(Appointment a : doctor.getAppointments()){
				AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
				appointmentDTO.setDate(a.getStart().toString().substring(0,10));
				appointmentDTO.setStartTime(a.getStartTime());
				appointmentDTO.setEndTime(a.getEndTime());

				if(a.getStatus().equals(AppointmentStatus.HAS_HAPPEND) && a.getClassification().equals(AppointmentClassification.NORMAL)){
					patients.add(a.getPatient().getEmail());
        		}

				if(a.getPatient() != null) {
					appointmentDTO.setPatientemail(a.getPatient().getEmail());
				}
        
				lista.add(appointmentDTO);
			}

			for ( Holiday h : doctor.getHolidays()){
				HolidayDTO holidayDTO = modelMapper.map(h,HolidayDTO.class);
				holidayDTO.setFromto(h.getStart().toString()+"-"+h.getEnd().toString());
				holidayDTOS.add(holidayDTO);
			}

			for (OperationRequest o : doctor.getOperations()) {
				OperationCalendarDTO operationCalendarDTO = modelMapper.map(o, OperationCalendarDTO.class);
				doctorDTO.getOperations().add(operationCalendarDTO);
			}

			if(doctor.getSingleratings().size() == 0){
				doctorDTO.setRating(0);
			} else {
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

		if(doctorRepository.save(doctor) != null){
			return modelMapper.map(doctor,DoctorDTO.class);
		} else{
			return  null;
		}
	}

	@Transactional
	public List<Doctor> findAllDoctors() {
		return doctorRepository.findAll();
	}

	public DoctorDTO findOneByPrincipal(Principal p){
		Doctor doctor = (Doctor) userService.findByUsername(p.getName());
		List<AppointmentDTO> lista = new ArrayList<>();
		List<HolidayDTO> holidayDTOS = new ArrayList<>();
		List<OperationCalendarDTO> operationsDTO = new ArrayList<>();

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

		for(OperationRequest operationRequest : doctor.getOperations()){
			//OperationRequestDTO orDTO = modelMapper.map(operationRequest,OperationRequestDTO.class);
			OperationCalendarDTO orDTO = new OperationCalendarDTO();
			orDTO.setId(operationRequest.getId());
			orDTO.setName(operationRequest.getName());
			orDTO.setStartTime(operationRequest.getStartTime());
			orDTO.setEndTime(operationRequest.getEndTime());
			orDTO.setDate(operationRequest.getStart().toString().substring(0,10));

			if(operationRequest.getPatient() != null) {
				orDTO.setPatientName(operationRequest.getPatient().getName());
				orDTO.setPatientLastname(operationRequest.getPatient().getLastname());
			}

			if(operationRequest.getDoctors() != null){
				List <String> docListName = new ArrayList<>();
				for(Doctor doc : operationRequest.getDoctors()){
					String docName = doc.getName() + " " + doc.getLastname();
					docListName.add(docName);
				}
				orDTO.setDoctorNames(docListName);
			}
			operationsDTO.add(orDTO);
		}

		lista.sort(Comparator.comparing(AppointmentDTO::getStart));

		DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
		doctorDTO.setAppointments(lista);
		doctorDTO.setHolidays(holidayDTOS);
		doctorDTO.setOperations(operationsDTO);

		return doctorDTO;
	}

	public List<DoctorDTO> getFreeDoctorsForOperation(OperationParamsDTO opParams){
		List<Doctor> doctors = doctorRepository.findAll();
		List<Doctor> operationDoctors = new ArrayList<>();

		for(Doctor doctor : doctors){
			if(doctor.getSpecialization().equals("Hirurg") || doctor.getSpecialization().equals("Anesteziolog")){
				operationDoctors.add(doctor);
			}
		}

		List<DoctorDTO> doctorDTOS = new ArrayList<>();

		String inputDateString = opParams.getDateOperation();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate inputDate = LocalDate.parse(inputDateString);
		java.sql.Date finalDate = java.sql.Date.valueOf(inputDate);
		LocalTime finalStartTime = LocalTime.parse(opParams.getTimeOperation());
		LocalTime finalEndTime = finalStartTime.plusHours(2);

		OperationRequest operation = operationRequestService.findOne(opParams.getRequestId());

		for(Doctor doctor : operationDoctors) {
			boolean isAvailable = true;
			//proveravam za godisnje odmore da li se preklapaju sa operacijom
			if(doctor.getHolidays() != null){
				for(Holiday h : doctor.getHolidays()){
					if((h.getStart().compareTo(finalDate)) == 0 || h.getEnd().compareTo(finalDate) ==0){
						isAvailable = false;
					} else if ((h.getStart().compareTo(finalDate) < 0) && (h.getEnd().compareTo(finalDate) > 0)){
						isAvailable = false;
					}
				}
			}

			//provera za radno vreme
			LocalTime startWork = doctor.getWorkingHours().getStart().toLocalTime();
			LocalTime endWork = doctor.getWorkingHours().getEnd().toLocalTime();

			if((finalStartTime.compareTo(startWork) < 0) || (finalEndTime.compareTo(endWork) > 0)){
				isAvailable = false;
			}

			//provera za ostale operacije
			if (doctor.getOperations() != null) {
				for (OperationRequest oprequest : doctor.getOperations()) {
					LocalTime opStart = oprequest.getStartTime().toLocalTime();
					LocalTime opEnd = oprequest.getEndTime().toLocalTime();

					if (oprequest.getStart().compareTo(finalDate) == 0) {
						//neka operacija pocinje pre kraja ove sto zelim i zavrsava
						if (((opStart.compareTo(finalStartTime) < 0) || (opStart.compareTo(finalStartTime) == 0))
								&& (opEnd.compareTo(finalStartTime) > 0) ) {
							isAvailable = false;
							break;
							//DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
							//doctorDTOS.add(doctorDTO);
						}

						if((opStart.compareTo(finalEndTime) < 0)
								&& (opEnd.compareTo(finalEndTime) > 0)) {
							isAvailable = false;
							break;
						}
					}
				}
			}

			if(isAvailable) {
				//DoctorDTO doctorDTO = modelMapper.map(doctor, DoctorDTO.class);
				DoctorDTO doctorDTO = new DoctorDTO(doctor);
				doctorDTOS.add(doctorDTO);
			}
		}
		return doctorDTOS;
	}
}

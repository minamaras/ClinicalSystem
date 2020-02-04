package com.example.ClinicalSystem.service;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.FilterDTO;
import com.example.ClinicalSystem.model.*;
import org.joda.time.LocalTime;
import org.modelmapper.Converter;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.repository.ClinicRepository;

import javax.transaction.Transactional;

@Service
public class ClinicService {

	@Autowired
	private ClinicRepository clinicRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ClinicAdminService clinicAdminService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private RatingService ratingService;


	public boolean addClinic(Clinic clinic) {

		if(findName(clinic.getName()) != null){
			return false;
		}

		clinicRepo.save(clinic);
		return true;
	}


	public List<Clinic> findAllClinics() {

		List<Clinic> clinics = clinicRepo.findAllByOrderByNameAsc();

		return clinics;
	}


	public  Clinic findClinic(ClinicDTO clinicDTO) {

		if(clinicRepo.findByName(clinicDTO.getName()) != null) {
			Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);


			if(clinic.getSingleratings().size() == 0){
				clinicDTO.setRating(0);
			}else {

				double suma = 0;

				for (Rating r : clinic.getSingleratings()) {
					suma = suma + r.getValue();
				}
				double rating = suma / (clinic.getSingleratings().size());
				clinicDTO.setRating(rating);
			}

			return clinic;
		}

		return null;
	}

	public Clinic findName(String name) {
		if(clinicRepo.findByName(name) != null) {

			return clinicRepo.findByName(name);
		}

		return null;
	}

	public  Clinic findClinic(Clinic clinic) {

		if(clinicRepo.findByName(clinic.getName()) != null) {
			return clinic;
		}

		return null;
	}

	public Clinic updateClinic(Clinic clinic) {

		return clinicRepo.save(clinic);
	}

	public ClinicDTO findClinic(String name) {

		Clinic clinic = clinicRepo.findByName(name);
		ClinicDTO clinicDTO = modelMapper.map(clinic, ClinicDTO.class);

		if(clinic.getSingleratings().size() == 0){
			clinicDTO.setRating(0);
		}else {

			double suma = 0;

			for (Rating r : clinic.getSingleratings()) {
				suma = suma + r.getValue();
			}
			double rating = suma / (clinic.getSingleratings().size());
			clinicDTO.setRating(rating);
		}



		return clinicDTO;
	}

	@Transactional
	public boolean addAdminToClinic(ClinicDTO clinicDTO, ClinicAdminDTO cadminDTO){
		Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);
		ClinicAdmin cAdmin = modelMapper.map(cadminDTO, ClinicAdmin.class);

		Authority authoritie = authorityService.findByname("CLINICADMIN");
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authoritie);
		cAdmin.setAuthorities(authorities);


		clinic.getClinicAdmins().add(cAdmin);
		cAdmin.setClinic(clinic);
		clinicRepo.save(clinic);

		if(cAdmin.getClinic() != null){
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public boolean addDoctorsToClinic(ClinicDTO clinicDTO, List<DoctorDTO> doctorDTOS) {
		Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);
		List<Doctor> doctors = new ArrayList<Doctor>();

		for(DoctorDTO ddto : doctorDTOS) {
			Doctor doctor = modelMapper.map(ddto, Doctor.class);
			doctor.setClinic(clinic);
			doctors.add(doctor);
		}

		clinic.setDoctors((Set<Doctor>) doctors);

		return true;
	}

	public boolean connectDoctorWithClinic(String name, DoctorDTO doctorDTO) {

		Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
		Clinic clinic = clinicRepo.findByName(name);

		if(doctor.getClinic() == null){
			clinic.getDoctors().add(doctor);
			clinicRepo.save(clinic);
		}

		return false;
	}

	public List<ClinicDTO> filterClinics(FilterDTO filter) throws ParseException {



		if(filter.getTime() != null){
			LocalTime time = new LocalTime(filter.getTime());
			filter.setStartAppointmentFilter(time);
		}

		List<ClinicDTO> finallist = new ArrayList<>();

		if(filter.getFilter() == null || filter.getFilter() == "" ){
			if(filter.getDate() != null && filter.getTime() != null && filter.getExamtype() != null){

				List<Clinic> clinics = clinicRepo.findAllByOrderByNameAsc();
				List<ClinicDTO> returnc = new ArrayList<>();


				String inputString =  filter.getDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
				LocalDate inputDate = LocalDate.parse(inputString);
				java.sql.Date finaldateFilter = java.sql.Date.valueOf(inputDate);



					for(Clinic c :clinics){
					List<String> lista = new ArrayList<>();

						for(Doctor d : c.getDoctors()) {

							Set<Holiday> doctorholidays = d.getHolidays();
							ArrayList<String> holidays = new ArrayList<>();

							if (d.getExamType().getName().equals(filter.getExamtype())) {

								for(Holiday h : doctorholidays){
									if(h.getHolidayRequestStatus() ==  HolidayRequestStatus.ACCEPTED) {

										String starth = h.getStart().toString().substring(0,10);
										String endh = h.getEnd().toString().substring(0,10);

										DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-mm-dd");
										LocalDate inputDates = LocalDate.parse(starth);
										LocalDate inputDatee = LocalDate.parse(endh);

										java.sql.Date finaldateStart = java.sql.Date.valueOf(inputDates);
										java.sql.Date finaldateEnd = java.sql.Date.valueOf(inputDatee);



									if(finaldateStart.equals(finaldateFilter) || finaldateEnd.equals(finaldateFilter) || finaldateStart.compareTo(finaldateFilter) > 0 ||  finaldateEnd.compareTo(finaldateFilter) <0){
										break;
									}

									}
								}


								LocalTime Dtimestart = new LocalTime(d.getStart());
								LocalTime Dtimeend = new LocalTime(d.getEnd());

								if ((Dtimestart.isBefore(filter.getStartAppointmentFilter()) || Dtimestart.isEqual(filter.getStartAppointmentFilter())) && (Dtimeend.isAfter(filter.getStartAppointmentFilter()))) {
									if(d.getAppointments().size() == 0){
										ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);

										if(c.getSingleratings().size() == 0){
											clinicDTO.setRating(0);
										}else {

											double suma = 0;

											for (Rating r : c.getSingleratings()) {
												suma = suma + r.getValue();
											}
											double rating = suma / (c.getSingleratings().size());
											clinicDTO.setRating(rating);
										}

										clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
										returnc.add(clinicDTO);
										break;
									}

									List<String> datelist = new ArrayList<>();
									for( Appointment ap: d.getAppointments()){
										datelist.add(ap.getStart().toString().substring(0,10));
									}

									if (!datelist.contains(filter.getDate())){
										ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);

										if(c.getSingleratings().size() == 0){
											clinicDTO.setRating(0);
										}else {

											double suma = 0;

											for (Rating r : c.getSingleratings()) {
												suma = suma + r.getValue();
											}
											double rating = suma / (c.getSingleratings().size());
											clinicDTO.setRating(rating);
										}

										clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
										returnc.add(clinicDTO);
										break;
									}

									for (Appointment a : d.getAppointments()) {
										if(a.getStatus() == AppointmentStatus.HAS_HAPPEND){
											continue;
										}
										String date = a.getStart().toString().substring(0, 10);
										if (a.getStart().toString().substring(0, 10).equals(filter.getDate())) {

											LocalTime Atimestart = new LocalTime(a.getStartTime());
											LocalTime Atimeend = new LocalTime(a.getEndTime());

											if ((filter.getStartAppointmentFilter().isEqual(Atimestart))) {

												lista.add("ne");

											} else if ((filter.getStartAppointmentFilter().isAfter(Atimestart)) && (filter.getStartAppointmentFilter().isBefore(Atimeend))) {
												lista.add("ne");
											}else if((filter.getStartAppointmentFilter().isBefore(Atimestart)) && (filter.getStartAppointmentFilter().plusMinutes(d.getExamType().getDuration()).isAfter(Atimestart))){
												lista.add("ne");
											}

											else {

												for(Appointment otherappointments : d.getAppointments()){

													if(otherappointments != a && otherappointments.getStart().toString().substring(0, 10).equals(filter.getDate())){

														LocalTime otherStart = new LocalTime(otherappointments.getStartTime());
														LocalTime otherEnd = new LocalTime(otherappointments.getEndTime());

														if(filter.getStartAppointmentFilter().isEqual(otherStart)){
															lista.add("ne");
														}
														else if(filter.getStartAppointmentFilter().isAfter(otherStart) && filter.getStartAppointmentFilter().isBefore(otherEnd)){
															lista.add("ne");
														}
														else if((filter.getStartAppointmentFilter().isBefore(otherStart)) && (filter.getStartAppointmentFilter().plusMinutes(d.getExamType().getDuration()).isAfter(otherStart))){
															lista.add("ne");
														}

														else{
															lista.add("da");
															ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);

															if(c.getSingleratings().size() == 0){
																clinicDTO.setRating(0);
															}else {

																double suma = 0;

																for (Rating r : c.getSingleratings()) {
																	suma = suma + r.getValue();
																}
																double rating = suma / (c.getSingleratings().size());
																clinicDTO.setRating(rating);
															}

															clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));

															if(returnc.size() == 0){
																returnc.add(clinicDTO);
															}else {

																for (ClinicDTO cdto : returnc) {
																	if (cdto.getName() != clinicDTO.getName()) {
																		returnc.add(clinicDTO);
																	}
																}
															}
														}

													}if(otherappointments == a && d.getAppointments().size() == 1){
														lista.add("da");
														ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);

														if(c.getSingleratings().size() == 0){
															clinicDTO.setRating(0);
														}else {

															double suma = 0;

															for (Rating r : c.getSingleratings()) {
																suma = suma + r.getValue();
															}
															double rating = suma / (c.getSingleratings().size());
															clinicDTO.setRating(rating);
														}

														clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
														returnc.add(clinicDTO);
													}

												}


											}

										}

									}


								}
							}
						}

						/*if(lista.contains("da")){

							returnc.add(c);
						}*/




				}

				for(ClinicDTO c: returnc) {

					finallist.add(c);

				}


				return finallist;

			}
		}else{

			List<Clinic> clinics = findAllClinics();

			List<ClinicDTO> returnc = filtering(clinics,filter);

			for(ClinicDTO c: returnc) {
				finallist.add(c);

			}

			return finallist;
		}

		return finallist;
	}


	public List<ClinicDTO> filtering(List<Clinic> allclinics,FilterDTO filter){

		List<ClinicDTO> returnc = new ArrayList<>();

		List<Clinic> clinics = new ArrayList<>();

		int broj =0;

		try{
			broj = Integer.parseInt(filter.getFilter());
		}catch (NumberFormatException e) {
			broj = 0;
		}

		for(Clinic c : allclinics) {

			double suma = 0;
			for(Rating r : c.getSingleratings()){
				suma = suma + r.getValue();
			}

			double rejting = suma/(c.getSingleratings().size());


			if (c.getName().toLowerCase().contains(filter.getFilter().toLowerCase())) {
				clinics.add(c);

			} else if (c.getAdress().toLowerCase().contains(filter.getFilter().toLowerCase())){
				clinics.add(c);
			} else if (rejting == broj) {

				clinics.add(c);
			}
		}

		if( clinics.size() == 0){
			return  returnc;
		}

		for(Clinic c :clinics){

			List<String> lista = new ArrayList<>();

				for(Doctor d : c.getDoctors()) {
					if (d.getExamType().getName().equals(filter.getExamtype())) {

						LocalTime Dtimestart = new LocalTime(d.getStart());
						LocalTime Dtimeend = new LocalTime(d.getEnd());

						if ((Dtimestart.isBefore(filter.getStartAppointmentFilter()) || Dtimestart.isEqual(filter.getStartAppointmentFilter())) && (Dtimeend.isAfter(filter.getStartAppointmentFilter()))) {
							if(d.getAppointments().size() == 0){
								ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);
								clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
								returnc.add(clinicDTO);
								break;
							}

							List<String> datelist = new ArrayList<>();
							for( Appointment ap: d.getAppointments()){
								datelist.add(ap.getStart().toString().substring(0,10));
							}

							if (!datelist.contains(filter.getDate())){
								ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);
								clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
								returnc.add(clinicDTO);
								break;
							}

							for (Appointment a : d.getAppointments()) {

								String date = a.getStart().toString().substring(0, 10);
								if (a.getStart().toString().substring(0, 10).equals(filter.getDate())) {

									LocalTime Atimestart = new LocalTime(a.getStartTime());
									LocalTime Atimeend = new LocalTime(a.getEndTime());

									if ((filter.getStartAppointmentFilter().isEqual(Atimestart))) {

										lista.add("ne");

									} else if ((filter.getStartAppointmentFilter().isAfter(Atimestart)) && (filter.getStartAppointmentFilter().isBefore(Atimeend))) {
										lista.add("ne");
									}
									else if((filter.getStartAppointmentFilter().isBefore(Atimestart)) && (filter.getStartAppointmentFilter().plusMinutes(d.getExamType().getDuration()).isAfter(Atimestart))){
										lista.add("ne");
									}

									else {

										for(Appointment otherappointments : d.getAppointments()){

											if(otherappointments != a){

												LocalTime otherStart = new LocalTime(otherappointments.getStartTime());
												LocalTime otherEnd = new LocalTime(otherappointments.getEndTime());

												if(filter.getStartAppointmentFilter().isEqual(otherStart)){
													lista.add("ne");
												}
												else if(filter.getStartAppointmentFilter().isAfter(otherStart) && filter.getStartAppointmentFilter().isBefore(otherEnd)){
													lista.add("ne");
												}
												else if((filter.getStartAppointmentFilter().isBefore(otherStart)) && (filter.getStartAppointmentFilter().plusMinutes(d.getExamType().getDuration()).isAfter(otherStart))){
													lista.add("ne");
												}
												else{
													lista.add("da");
													ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);
													clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));

													if(returnc.size() == 0){
														returnc.add(clinicDTO);
													}else {

														for (ClinicDTO cdto : returnc) {
															if (cdto.getName() != clinicDTO.getName()) {
																returnc.add(clinicDTO);
															}
														}
													}
												}

											}
											if(otherappointments == a && d.getAppointments().size() == 1){
												lista.add("da");
												ClinicDTO clinicDTO = modelMapper.map(c,ClinicDTO.class);
												clinicDTO.setExamprice(String.valueOf(d.getExamType().getPrice()));
												returnc.add(clinicDTO);
											}
										}


									}

								}
							}


						}
					}
				}


		}

	return returnc;
	}



	public ClinicDTO updatedrating(Long id,int rating){

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) a.getPrincipal();
		Patient p = patientService.findPatient(user.getEmail());

		Optional<Clinic> clinicop = clinicRepo.findById(id);
		Clinic clinic = clinicop.get();

		clinic.getPatientsThatRated().add(p);
		p.getRatedClinics().add(clinic);

		Rating dodatrejting = new Rating();
		dodatrejting.setValue(rating);

		ratingService.save(dodatrejting);

		clinic.getSingleratings().add(dodatrejting);

		double suma = 0;
		for(Rating r : clinic.getSingleratings()){
			suma = suma + r.getValue();
		}

		double novirejting = suma/(clinic.getSingleratings().size());

		//doctor.setRating(novirejting);

		if(clinicRepo.save(clinic) != null){
			return modelMapper.map(clinic,ClinicDTO.class);
		}else{
			return  null;
		}
	}
}

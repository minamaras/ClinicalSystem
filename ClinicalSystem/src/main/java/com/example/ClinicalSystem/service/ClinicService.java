package com.example.ClinicalSystem.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.DTO.ClinicDTO;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.repository.ClinicRepository;

import javax.transaction.Transactional;

@Service
public class ClinicService {

	@Autowired
	ClinicRepository clinicRepo;

	@Autowired
	ModelMapper modelMapper;

	public Clinic addClinic(ClinicDTO clinicDto) {

		Clinic clinic =  modelMapper.map(clinicDto, Clinic.class);

		return clinicRepo.save(clinic);
	}

	public List<ClinicDTO> findAllClinics() {

		List<Clinic> clinics = clinicRepo.findAll();

		List<ClinicDTO> clinicsDTO = new ArrayList<>();
		for (Clinic c : clinics) {
			clinicsDTO.add(new ClinicDTO(c));
		}

		return clinicsDTO;
	}


	public  Clinic findClinic(ClinicDTO clinicDTO) {

		if(clinicRepo.findByName(clinicDTO.getName()) != null) {
			Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);
			return clinic;
		}

		return null;
	}
	public Clinic updateClinic(ClinicDTO clinicDto) {

		Clinic clinic = clinicRepo.findByName(clinicDto.getName());
		if(clinic == null) {
			return null;
		}
		clinic.setAdress(clinicDto.getAdress());
		clinic.setDescription(clinicDto.getDescription());

		return clinicRepo.save(clinic);
	}

	public ClinicDTO findClinic(String name) {
		Clinic clinic = clinicRepo.findByName(name);
		ClinicDTO clinicDTO = modelMapper.map(clinic, ClinicDTO.class);
		return clinicDTO;
	}

	@Transactional
	public boolean addAdminToClinic(ClinicDTO clinicDTO, ClinicAdminDTO cadminDTO){
		Clinic clinic = modelMapper.map(clinicDTO, Clinic.class);
		ClinicAdmin cAdmin = modelMapper.map(cadminDTO, ClinicAdmin.class);


		clinic.setClinicAdmin(cAdmin);
		cAdmin.setClinics(clinic);


		if((clinic.getClinicAdmin() != null) && (cAdmin.getClinic() != null)){
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
}

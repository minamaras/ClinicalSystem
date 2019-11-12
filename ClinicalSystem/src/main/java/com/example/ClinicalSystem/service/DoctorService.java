package com.example.ClinicalSystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.repository.DoctorRepository;

@Service
public class DoctorService {
	
	@Autowired
	DoctorRepository doctorRepository;
	
	public List<Doctor> findAll() {
		return doctorRepository.findAll();
	}
	
	public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

}

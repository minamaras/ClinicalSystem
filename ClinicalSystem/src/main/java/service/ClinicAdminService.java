package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.ClinicAdmin;
import model.Doctor;
import repository.ClinicAdminRepository;
import repository.DoctorRepository;

@Service
public class ClinicAdminService {
	
	
	@Autowired
	ClinicAdminRepository clinicAdminRepository;
	DoctorRepository doctorRepository;
	
	public List<ClinicAdmin> findAll() {
		return clinicAdminRepository.findAll();
	}
	
	public ClinicAdmin save(ClinicAdmin ca) {
		return clinicAdminRepository.save(ca);
	}
	
	public Doctor addDoctor(Doctor d) {
		return doctorRepository.save(d);
	}

}

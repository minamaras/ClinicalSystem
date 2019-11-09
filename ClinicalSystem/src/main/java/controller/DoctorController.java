package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dto.DoctorDTO;
import model.Doctor;
import service.DoctorService;

@RestController
@RequestMapping(value = "api/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorService doctorService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<DoctorDTO>> getAllDoctors() {

		List<Doctor> doctors = doctorService.findAll();

		// convert students to DTOs
		List<DoctorDTO> doctorsDTO = new ArrayList<>();
		for (Doctor d : doctors) {
			doctorsDTO.add(new DoctorDTO(d));
		}

		return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<DoctorDTO> saveDoctor(@RequestBody DoctorDTO doctorDTO) {
		
		Doctor doctor = new Doctor();
		doctor.setId(doctorDTO.getId());
		doctor.setName(doctorDTO.getFirstName());
		doctor.setLastname(doctorDTO.getLastName());
		doctor.setEmail(doctorDTO.getEmail());
		doctor.setClinic(doctorDTO.getClinic());
		
		doctor = doctorService.save(doctor);
		return new ResponseEntity<>(new DoctorDTO(doctor), HttpStatus.CREATED);
	}

}

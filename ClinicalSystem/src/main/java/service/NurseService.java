package service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Nurse;
import repository.NurseRepository;

@Service
public class NurseService {

	@Autowired
	private NurseRepository nurseRepository;
	
	public List<Nurse> findAll(){
		return nurseRepository.findAll();
	
	}
	
	public Nurse save(Nurse nurse) {
		return nurseRepository.save(nurse);
	}
	
}

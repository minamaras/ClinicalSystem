package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.MedicationDTO;
import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public boolean addMedication(Medication medication){

        if(findByName(medication.getName()) != null){
            return false;
        }
        medicationRepository.save(medication);
        return true;
    }

    public List<Medication> findAll(){
        return medicationRepository.findAllByOrderByNameAsc();
    }

    public Medication findByName(String name){
        return medicationRepository.findByName(name);
    }

    public List<MedicationDTO> findAllDTOs(){
        List<Medication> medications = medicationRepository.findAllByOrderByNameAsc();
        List<MedicationDTO> medicationDTOS = new ArrayList<>();

        for(Medication med: medications){
            MedicationDTO medicationDTO = new MedicationDTO(med);
            medicationDTOS.add(medicationDTO);
        }

        return medicationDTOS;
    }
}

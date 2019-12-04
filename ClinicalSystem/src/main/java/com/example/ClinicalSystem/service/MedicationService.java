package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public Medication addMedication(Medication medication){
        return medicationRepository.save(medication);
    }

    public List<Medication> findAll(){
        return medicationRepository.findAll();
    }

    public Medication findByName(String name){
        return medicationRepository.findByName(name);
    }
}

package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis addDiagnosis(Diagnosis diagnosis){
        return diagnosisRepository.save(diagnosis);
    }

    public List<Diagnosis> findAll(){
        return diagnosisRepository.findAll();
    }

    public Diagnosis findByName(String name){
        return diagnosisRepository.findByName(name);
    }

}

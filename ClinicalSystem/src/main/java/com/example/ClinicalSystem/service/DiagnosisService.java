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

    public boolean addDiagnosis(Diagnosis diagnosis){

        if(findByName(diagnosis.getName()) != null){
            return false;
        }
        diagnosisRepository.save(diagnosis);
        return true;
    }

    public List<Diagnosis> findAll(){
        return diagnosisRepository.findAll();
    }

    public Diagnosis findByName(String name){
        return diagnosisRepository.findByName(name);
    }

}

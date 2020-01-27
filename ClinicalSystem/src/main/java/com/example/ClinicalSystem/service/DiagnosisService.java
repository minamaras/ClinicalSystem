package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DiagnosisDTO;
import com.example.ClinicalSystem.model.Diagnosis;
import com.example.ClinicalSystem.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<DiagnosisDTO> findAllDTOs(){
        List<Diagnosis> diagnosis = diagnosisRepository.findAllByOrderByNameAsc();
        List<DiagnosisDTO> diagnosisDTOS = new ArrayList<>();
        for(Diagnosis d: diagnosis){
            DiagnosisDTO diagnosisDTO = new DiagnosisDTO(d);
            diagnosisDTOS.add(diagnosisDTO);
        }
        return diagnosisDTOS;
    }

}

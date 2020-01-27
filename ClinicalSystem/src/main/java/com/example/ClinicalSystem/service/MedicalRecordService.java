package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.MedicalRecordDTO;
import com.example.ClinicalSystem.model.MedicalRecord;
import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientService patientService;

    public MedicalRecord findByPatient(Patient patient){
        return medicalRecordRepository.findByPatient(patient);
    }

    public MedicalRecord findById(long id){
        return medicalRecordRepository.findById(id);
    }

    public MedicalRecord saveRecord(MedicalRecord medicalRecord){
        return medicalRecordRepository.save(medicalRecord);
    }

    public Boolean changeBasicInfo(MedicalRecordDTO medicalRecordDTO){

        Patient patient = patientService.findPatient(medicalRecordDTO.getPatientemail());
        MedicalRecord medRec = findById(patient.getMedicalRecord().getId());

        if(medRec == null){
            return false;
        }
        medRec.setAdditional(medicalRecordDTO.getAdditional());
        medRec.setAllergies(medicalRecordDTO.getAllergies());
        medRec.setBloodtype(medicalRecordDTO.getBloodtype());
        medRec.setEyes(medicalRecordDTO.getEyes());
        medRec.setMeasures(medicalRecordDTO.getMeasures());

        saveRecord(medRec);

        return true;

    }

}

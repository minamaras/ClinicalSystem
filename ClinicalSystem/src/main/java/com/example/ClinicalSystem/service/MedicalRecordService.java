package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorVistisDTO;
import com.example.ClinicalSystem.DTO.MedicalRecordDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.MedicalRecordRepository;
import com.example.ClinicalSystem.repository.MedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

    public List<MedicalRecordDTO> findAll() {

        List<MedicalRecord> records = medicalRecordRepository.findAll();
        List<MedicalRecordDTO> recordsDTO = new ArrayList<>();

        for (MedicalRecord r : records) {

            MedicalRecordDTO medicalRecordDTO = modelMapper.map(r, MedicalRecordDTO.class);
            recordsDTO.add(medicalRecordDTO);
        }

        return recordsDTO;

    }

    public MedicalRecordDTO findRecord(Long id){

        Optional<Patient> patient = patientService.findPatientById(id);
       MedicalRecord mr = medicalRecordRepository.findById(patient.get().getMedicalRecord().getId());
       MedicalRecordDTO medicalRecordDTO = modelMapper.map(mr, MedicalRecordDTO.class);

       Set<DoctorVistisDTO> doctorVList = new HashSet<>();
       for(DoctorVisits dv : mr.getDoctorVisits()){

           DoctorVistisDTO doctorVistisDTO = modelMapper.map(dv,DoctorVistisDTO.class);
           doctorVistisDTO.setSomedate(dv.getVisitDate().toString());
           doctorVList.add(doctorVistisDTO);
       }

       Set<String> diseaselist = new HashSet<>();
        for(Disease d : mr.getDiseases()){
            diseaselist.add(d.getName());
        }

        medicalRecordDTO.setDoctorVisits(doctorVList);
        medicalRecordDTO.setDiseases(diseaselist);

        return medicalRecordDTO;

    }


}

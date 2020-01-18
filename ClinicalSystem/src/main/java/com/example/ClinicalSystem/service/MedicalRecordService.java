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


}

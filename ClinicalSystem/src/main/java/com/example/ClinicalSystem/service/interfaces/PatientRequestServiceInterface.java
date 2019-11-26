package com.example.ClinicalSystem.service.interfaces;

import com.example.ClinicalSystem.DTO.PatientRequestDTO;
import com.example.ClinicalSystem.model.PatientRequest;

public interface PatientRequestServiceInterface {

    boolean emailExistsInDB(PatientRequestDTO patientRequestDTO);

    boolean AddPatientRequest(PatientRequestDTO patientRequestDTO);

    PatientRequestDTO findByEmail(String email);

}


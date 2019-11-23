package com.example.ClinicalSystem.service.interfaces;

import com.example.ClinicalSystem.DTO.PatientRequestDTO;

public interface PatientRequestServiceInterface {

    boolean emailExistsInDB(PatientRequestDTO patientRequestDTO);

    boolean AddPatientRequest(PatientRequestDTO patientRequestDTO);

}


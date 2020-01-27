package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisNamesDTO {

    private String name;

    public DiagnosisNamesDTO(String name) {
        this.name = name;
    }

    public DiagnosisNamesDTO(Diagnosis diagnosis){
        this.name = diagnosis.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

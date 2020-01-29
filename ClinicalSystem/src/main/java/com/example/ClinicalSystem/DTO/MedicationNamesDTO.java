package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Medication;

public class MedicationNamesDTO {

    private String name;

    public MedicationNamesDTO(){
        super();
    }

    public MedicationNamesDTO(String name) {
        this.name = name;
    }

    public MedicationNamesDTO(Medication medication){
        this.name = medication.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

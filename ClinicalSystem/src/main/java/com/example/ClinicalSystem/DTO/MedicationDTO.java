package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Medication;

public class MedicationDTO {

    private String name;
    private String text;

    public MedicationDTO(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public MedicationDTO(){
        super();
    }

    public MedicationDTO(Medication medication){
        this.name = medication.getName();
        this.text = medication.getText();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

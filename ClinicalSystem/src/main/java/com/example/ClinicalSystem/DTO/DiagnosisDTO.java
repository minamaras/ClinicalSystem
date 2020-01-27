package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Diagnosis;

public class DiagnosisDTO {

    private String name;
    private String text;

    public DiagnosisDTO(){
        super();
    }

    public DiagnosisDTO(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public DiagnosisDTO(Diagnosis diagnosis){
        this.name = diagnosis.getName();
        this.text = diagnosis.getText();
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

package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Medication;
import com.example.ClinicalSystem.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {

    private Long id;
    private String doctoremail;
    private String nurseemail;
    private String patientemail;
    private boolean auth;
    private String content;
    private List<String> medicationName = new ArrayList<>();
    
    public RecipeDTO() {
        super();
    }

    public RecipeDTO(Recipe recipe){
        this.id = recipe.getId();
        this.doctoremail = recipe.getDoctor().getEmail();
        this.patientemail = recipe.getPatient().getEmail();
        if(recipe.getNurse() != null) {
            this.nurseemail = recipe.getNurse().getEmail();
        }
        this.auth = recipe.isAuth();
        this.content = recipe.getContent();
        for(Medication med : recipe.getMedications()){
            this.medicationName.add(med.getName());
        }
    }

    public RecipeDTO(Long id, String demail, String pemail, boolean auth, String content){
        this.id = id;
        this.doctoremail = demail;
        this.patientemail = pemail;
        this.auth = auth;
        this.content = content;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctoremail() {
        return doctoremail;
    }

    public void setDoctoremail(String doctoremail) {
        this.doctoremail = doctoremail;
    }

    public String getNurseemail() {
        return nurseemail;
    }

    public void setNurseemail(String nurseemail) {
        this.nurseemail = nurseemail;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(List<String> medicationName) {
        this.medicationName = medicationName;
    }
}

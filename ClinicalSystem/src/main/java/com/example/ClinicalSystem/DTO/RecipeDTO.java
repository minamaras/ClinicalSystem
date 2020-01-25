package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Recipe;

public class RecipeDTO {

    private Long id;
    private String doctoremail;
    private String nurseemail;
    private String patientemail;
    private boolean auth;
    private String content;
    
    public RecipeDTO() {
        super();
    }

    public RecipeDTO(Recipe recipe){
        this(recipe.getId(), recipe.getDoctor().getEmail(), recipe.getPatient().getEmail(), recipe.isAuth(), recipe.getContent());
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
}

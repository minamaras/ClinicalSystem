package com.example.ClinicalSystem.DTO;

public class ChangePasswordDTO {

    private String email;
    private String oldpassword;
    private String newpassword;

    public ChangePasswordDTO(){
        super();
    }

    public ChangePasswordDTO(String email, String oldpassword, String newpassword) {
        this.email = email;
        this.oldpassword = oldpassword;
        this.newpassword = newpassword;
    }

    public ChangePasswordDTO(ChangePasswordDTO changePasswordDTO){
        this.email = changePasswordDTO.getEmail();
        this.oldpassword = changePasswordDTO.getOldpassword();
        this.newpassword = changePasswordDTO.getNewpassword();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}

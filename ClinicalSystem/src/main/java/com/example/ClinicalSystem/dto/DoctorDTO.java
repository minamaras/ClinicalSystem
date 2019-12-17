package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.Role;

public class DoctorDTO {

    private Long Id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String specialization;
    private Long clinicid;
    private String clinicname;
    private int rating;


    public DoctorDTO() {
    	this.role = role.DOCTOR;

    }

    public DoctorDTO(Doctor d) {
        this(d.getId(), d.getName(), d.getLastname(), d.getEmail(), d.getPassword(), d.getRole(), d.getSpecialization(), d.getRating());
    }

    public DoctorDTO(Long id, String name, String lastname, String email, String password, Role role, String specialization, int rating) {
        Id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role.DOCTOR;
        this.specialization = specialization;
        this.rating = rating;

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getClinicid() {
        return clinicid;
    }

    public void setClinicid(Long clinicid) {
        this.clinicid = clinicid;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }
}

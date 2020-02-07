package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.*;

import java.sql.Time;
import java.util.*;

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
    private double rating;
    private ExamTypeDTO examType;
    private Time start;
    private Time end;
    private List<AppointmentDTO> appointments = new ArrayList<>();
    private boolean firstLogin;
    private Set<HolidayDTO> holidays = new HashSet<>();
    private List<String> patients = new ArrayList<>();
    private List<OperationCalendarDTO> operations = new ArrayList<>();


    public DoctorDTO() {
    	this.role = role.DOCTOR;
    	this.rating = 0;
    	this.firstLogin = true;

    }


    public DoctorDTO(Long id, String name, String lastname, String email, String password, Role role, String specialization, double rating, Time start, Time end, boolean firstLogin) {
        Id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role.DOCTOR;
        this.specialization = specialization;
        this.rating = rating;
        this.start = start;
        this.end = end;
        this.firstLogin = firstLogin;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
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


    public ExamTypeDTO getExamType() {
        return examType;
    }

    public void setExamType(ExamTypeDTO examType) {
        this.examType = examType;
    }

    public Date getStart(){
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;

    }

    public List<AppointmentDTO> getAppointments() {

        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public Set<HolidayDTO> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<HolidayDTO> holidays) {
        this.holidays = holidays;
    }

    public List<String> getPatients() {
        return patients;
    }

    public void setPatients(List<String> patients) {
        this.patients = patients;
    }

    public List<OperationCalendarDTO> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationCalendarDTO> operations) {
        this.operations = operations;
    }
}

package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Nurse;
import com.example.ClinicalSystem.model.Role;

public class NurseDTO {
	
	private Long id;
	private String name;
	private String lastname;
	private String email;
	private String clinicid;
	private Role role = Role.NURSE;
	private String password;
	private boolean firstLogin;

	public NurseDTO(){
		super();
		this.role = role.NURSE;
		this.firstLogin = true;
	}

	public NurseDTO(Nurse nurse) {
		this(nurse.getId(), nurse.getName(), nurse.getLastname(), nurse.getEmail(), nurse.isFirstLogin());
	}
	
	public NurseDTO(Long id, String firstName, String lastName, String email, boolean firstLogin) {
		this.id = id;
		this.name = firstName;
		this.lastname = lastName;
		this.email = email;
		this.firstLogin = firstLogin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String firstName) {
		this.name = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClinicid() {
		return clinicid;
	}

	public void setClinicid(String clinicid) {
		this.clinicid = clinicid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}

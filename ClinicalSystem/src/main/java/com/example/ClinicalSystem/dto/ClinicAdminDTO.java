package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Role;

public class ClinicAdminDTO {
	
	private Long id;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private ClinicDTO clinic;
	private Role role = Role.CLINICADMIN;
	private boolean firstLogin;

	public ClinicAdminDTO() {
		super();
	}

	
	public ClinicAdminDTO(Long id, String firstName, String lastName, String email, String password, ClinicDTO clinic,Role role, boolean firstLogin) {
		super();
		this.id = id;
		this.name = firstName;
		this.lastname = lastName;
		this.email = email;
		this.password = password;
		this.clinic = clinic;
		this.role=role;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClinicDTO getClinic() {
		return clinic;
	}

	public void setClinic(ClinicDTO clinic) {
		this.clinic = clinic;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}

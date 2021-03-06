package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.model.Role;

public class ClinicalCentreAdminDTO {

	private Long id;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private Role role = Role.CLINICALCENTREADMIN;
	private boolean firstLogin;
	
	public ClinicalCentreAdminDTO() {
		super();
		this.role = role.CLINICALCENTREADMIN;
		this.firstLogin = true;
	}

	public ClinicalCentreAdminDTO(ClinicalCentreAdmin ccaDTO) {
		this.id = ccaDTO.getId();
		this.name = ccaDTO.getName();
		this.lastname = ccaDTO.getLastname();
		this.email = ccaDTO.getEmail();
		this.password = ccaDTO.getPassword();
		this.firstLogin = ccaDTO.isFirstLogin();
	}
	
	
	public ClinicalCentreAdminDTO(Long id, String firstName, String lastName, String email, String password, boolean firstLogin) {
		this.id = id;
		this.name = firstName;
		this.lastname = lastName;
		this.email = email;
		this.password = password;
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
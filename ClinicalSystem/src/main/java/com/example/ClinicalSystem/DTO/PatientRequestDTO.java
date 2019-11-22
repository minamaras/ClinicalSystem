package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Role;

public class PatientRequestDTO {
	
	private Long Id;
	
	private String name;
	
	private String lastname;
	
	private String email;
	
	private String password;
	
	private Role role;

	public PatientRequestDTO(Long id, String name, String lastname, String email, String password, Role role) {
		super();
		Id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public PatientRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
	

}

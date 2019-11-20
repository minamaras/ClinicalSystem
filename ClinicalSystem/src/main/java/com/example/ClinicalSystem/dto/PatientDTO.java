package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Patient;

public class PatientDTO {
	
	private Long Id;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private String role;
	
	public PatientDTO(Patient p) {
		this(p.getId(), p.getName(), p.getLastname(), p.getEmail(), p.getPassword());
	}
	public PatientDTO() {
	}


	public PatientDTO(Long id, String name, String lastname, String email, String password) {
		super();
		Id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;

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


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
	

}

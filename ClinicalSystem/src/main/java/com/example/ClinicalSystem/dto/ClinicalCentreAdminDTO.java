package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ClinicalCentreAdmin;

public class ClinicalCentreAdminDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
	public ClinicalCentreAdminDTO() {
		super();
	}
	
	public ClinicalCentreAdminDTO(ClinicalCentreAdmin clinicalCentreAdmin) {
		this(clinicalCentreAdmin.getId(), clinicalCentreAdmin.getName(), clinicalCentreAdmin.getLastname(), clinicalCentreAdmin.getEmail());
	}
	
	public ClinicalCentreAdminDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

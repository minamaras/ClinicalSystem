package com.example.ClinicalSystem.DTO;

public class ClinicAdminDTO {
	
	private Long id;
	private String name;
	private String lastName;
	private String email;
	private String password;
	
	public ClinicAdminDTO() {
		super();
	}
	
	public ClinicAdminDTO(Long id, String firstName, String lastName, String email, String password) {
		super();
		this.id = id;
		this.name = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
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

	public void setFirstName(String firstName) {
		this.name = firstName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}

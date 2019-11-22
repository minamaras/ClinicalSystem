package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Clinic;

public class ClinicAdminDTO {

	private Long id;
	private String name;
	private String lastName;

	private String email;

	private String password;
	private Clinic clinic;

	public ClinicAdminDTO() {
		super();
	}

	public ClinicAdminDTO(ClinicAdmin clinicAdmin) {

		this(clinicAdmin.getId(), clinicAdmin.getName(), clinicAdmin.getLastname(),clinicAdmin.getEmail(), clinicAdmin.getPassword(), clinicAdmin.getClinic());
	}

	public ClinicAdminDTO(Long id, String firstName, String lastName, String email, String password, Clinic clinic) {
		super();
		this.id = id;
		this.name = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.clinic = clinic;
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
		return lastName;
	}

	public void setLastname(String lastName) {
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

	public Clinic getClinic() { return clinic; }

	public void setClinic(Clinic clinic) { this.clinic = clinic; }






}

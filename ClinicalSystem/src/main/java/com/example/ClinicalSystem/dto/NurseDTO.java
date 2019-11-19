package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.Nurse;

public class NurseDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Clinic clinic;
	
	public NurseDTO() {
		super();
	}
	
	public NurseDTO(Nurse nurse) {
		this(nurse.getId(), nurse.getName(), nurse.getLastname(), nurse.getEmail(), nurse.getClinic());
	}
	
	public NurseDTO(Long id, String firstName, String lastName, String email, Clinic clinic) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.clinic = clinic;
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

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	
	
}

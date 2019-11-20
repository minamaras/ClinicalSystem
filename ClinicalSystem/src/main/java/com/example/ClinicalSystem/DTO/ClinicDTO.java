package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Clinic;

public class ClinicDTO {
	
	private Long id;
	private String name;
	private String adress;
	private String description;
	
	public ClinicDTO() {
		super();
	}
	
	public ClinicDTO(Clinic clinic) {
		this(clinic.getId(), clinic.getName(), clinic.getAdress(), clinic.getDescription());
	}
	
	public ClinicDTO(Long id, String name, String adress, String description) {
		super();
		this.id = id;
		this.name = name;
		this.adress = adress;
		this.description = description;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}

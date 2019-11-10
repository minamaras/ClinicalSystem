package com.example.ClinicalSystem.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class ClinicAdmin extends User {

	@OneToMany(mappedBy = "clinicadmin", fetch = FetchType.LAZY)
	private String clinics;

	public ClinicAdmin(String clinics) {
		super();
		this.setRole(Role.CLINICADMIN);
		this.clinics = clinics;
	}

	public ClinicAdmin() {
		super();
		this.setRole(Role.CLINICADMIN);
	}

	public String getClinics() {
		return clinics;
	}

	public void setClinics(String clinics) {
		this.clinics = clinics;
	}





}

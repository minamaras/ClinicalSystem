package com.example.ClinicalSystem.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ClinicAdmin extends User {

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Clinic clinic;

	public ClinicAdmin(Clinic clinic) {
		super();
		this.setRole(Role.CLINICADMIN);
		this.clinic = clinic;
	}

	public ClinicAdmin() {
		super();
		this.setRole(Role.CLINICADMIN);
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinics(Clinic clinic) {
		this.clinic = clinic;
	}





}

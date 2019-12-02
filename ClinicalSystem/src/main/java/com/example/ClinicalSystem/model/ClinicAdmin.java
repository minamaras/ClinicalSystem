package com.example.ClinicalSystem.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ClinicAdmin extends User {

	@ManyToOne//(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

}

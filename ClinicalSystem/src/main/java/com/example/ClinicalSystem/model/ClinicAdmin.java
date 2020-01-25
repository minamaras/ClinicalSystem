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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Doctor> doctors = new HashSet<Doctor>();

	@Column(name="firstlogin")
	private boolean firstlogin;

	public ClinicAdmin(Clinic clinic) {
		super();
		this.setRole(Role.CLINICADMIN);
		this.clinic = clinic;
	}

	public ClinicAdmin() {
		super();
		this.setRole(Role.CLINICADMIN);
		this.firstlogin = true;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public boolean isFirstlogin() {
		return firstlogin;
	}

	public void setFirstlogin(boolean firstlogin) {
		this.firstlogin = firstlogin;
	}

}

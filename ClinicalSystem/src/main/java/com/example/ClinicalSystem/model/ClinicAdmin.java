package com.example.ClinicalSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class ClinicAdmin extends User {

	@OneToMany(mappedBy = "clinicadmin", fetch = FetchType.LAZY)
	private Set<Clinic> clinics;


	public ClinicAdmin(Set<Clinic> clinics) {
		super();
		this.setRole(Role.CLINICADMIN);
		this.clinics = clinics;
	}

	public ClinicAdmin() {
		super();
		this.setRole(Role.CLINICADMIN);
	}

	public Set<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(Set<Clinic> clinics) {
		this.clinics = clinics;
	}
	
	
	
	
	
}

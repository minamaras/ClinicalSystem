package com.example.ClinicalSystem.model;

import javax.persistence.Entity;

@Entity
public class ClinicalCentreAdmin extends User {
	

	public ClinicalCentreAdmin() {
			super();
			this.setRole(Role.CLINICALCENTREADMIN);

	}



}

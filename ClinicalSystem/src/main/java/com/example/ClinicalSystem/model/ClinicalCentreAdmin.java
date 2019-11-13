package com.example.ClinicalSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClinicalCentreAdmin extends User {
	
	
	public ClinicalCentreAdmin() {
			super();
			this.setRole(Role.CLINICALCENTREADMIN);
		
	}
	
	

}

package com.example.ClinicalSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ClinicalCentreAdmin extends User {

	@Column(name="firstlogin")
	private boolean firstLogin;
	

	public ClinicalCentreAdmin() {
			super();
			this.firstLogin = true;
			this.setRole(Role.CLINICALCENTREADMIN);

	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}

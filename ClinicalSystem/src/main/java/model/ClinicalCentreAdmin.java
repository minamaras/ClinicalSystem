package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class ClinicalCentreAdmin extends User {
	
	
	public ClinicalCentreAdmin() {
			super();
			this.setRole(Role.CLINICALCENTREADMIN);
		
	}
	
	

}

package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ClinicAdmin extends User {

	@OneToMany(mappedBy = "clinicadmin", fetch = FetchType.LAZY)
	private String clinics;

	public ClinicAdmin(String clinics) {
		super();
		this.clinics = clinics;
	}

	public ClinicAdmin() {
		super();
	}

	public String getClinics() {
		return clinics;
	}

	public void setClinics(String clinics) {
		this.clinics = clinics;
	}
	
	
	
	
	
}

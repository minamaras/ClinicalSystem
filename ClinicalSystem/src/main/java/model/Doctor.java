package model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Doctor extends User {
	
	
	@Column(name = "specialization", nullable = false)
	private String specialization;
	
	@Column(name = "rating", nullable = false)
	private String rating;
	
	@ManyToMany(mappedBy = "doctor", fetch = FetchType.LAZY )
	private Set<Patient> patients = new HashSet<Patient>();
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MedicalRecord medicalRecord;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Calendar calendar;
	

	public Doctor() {
		super();
	}

	public Doctor(String specialization, String rating, Set<Patient> patients, Clinic clinic,
			MedicalRecord medicalRecord, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.rating = rating;
		this.patients = patients;
		this.clinic = clinic;
		this.medicalRecord = medicalRecord;
		this.calendar = calendar;
	}

	public Doctor(String specialization, Clinic clinic, Calendar calendar) {
		super();
		this.specialization = specialization;
		this.clinic = clinic;
		this.calendar = calendar;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}	
}

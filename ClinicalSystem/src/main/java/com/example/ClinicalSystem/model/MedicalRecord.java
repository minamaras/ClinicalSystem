package com.example.ClinicalSystem.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="medical_record")
public class MedicalRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToOne(mappedBy ="medicalRecord")
	private Patient patient;

	@ManyToMany
	@JoinTable(name = "disease_record", joinColumns = @JoinColumn(name = "record_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"))
	private Set<Disease> diseases = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<DoctorVisits> doctorVisits = new HashSet<>();

	@Column(name = "medicalhistory", nullable = false)
	private String medicalHistory;

	@ManyToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	@JoinTable(name = "medicalrecord_doctor", joinColumns = @JoinColumn(name = "medicalrecord_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private Set<Doctor> doctors;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}


	public Set<DoctorVisits> getDoctorVisits() {
		return doctorVisits;
	}

	public void setDoctorVisits(Set<DoctorVisits> doctorVisits) {
		this.doctorVisits = doctorVisits;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Set<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(Set<Disease> diseases) {
		this.diseases = diseases;
	}
}

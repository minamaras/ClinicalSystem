package com.example.ClinicalSystem.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BusinessReport {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "rating" , nullable = false)
	private int rating;
	
	@Column(name = "doctorrating" , nullable = false)
	private String doctorrating;
	
	@Column(name = "income" , nullable = false)
	private String income;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Clinic clinic;
	
	public BusinessReport(Long id, int rating, String doctorrating, String income, Clinic clinic) {
		super();
		this.id = id;
		this.rating = rating;
		this.doctorrating = doctorrating;
		this.income = income;
		this.clinic = clinic;
	}

	public BusinessReport() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getDoctorrating() {
		return doctorrating;
	}

	public void setDoctorrating(String doctorrating) {
		this.doctorrating = doctorrating;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}
	
	
	
	
	
	


}

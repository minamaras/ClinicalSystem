package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Patient;
import com.example.ClinicalSystem.model.Role;

public class PatientDTO {
	
	private Long Id;

	private String name;

	private String lastname;

	private String email;

	private String password;

	private Role role = Role.PATIENT;

	private String adress;


	private String city;


	private String country;


	private String phone;

	private String repatedPassword;

	private String socialSecurityNumber;
	
	public PatientDTO(Patient p) {
		this(p.getId(), p.getName(), p.getLastname(), p.getEmail(), p.getPassword(), p.getSocialSecurityNumber());
	}
	public PatientDTO() {
		
	}


	public PatientDTO(Long id, String name, String lastname, String email, String password, String socialSecurityNumber) {
		super();
		Id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.socialSecurityNumber = socialSecurityNumber;

	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public String getRepatedPassword() {
		return repatedPassword;
	}

	public void setRepatedPassword(String repatedPassword) {
		this.repatedPassword = repatedPassword;
	}
}

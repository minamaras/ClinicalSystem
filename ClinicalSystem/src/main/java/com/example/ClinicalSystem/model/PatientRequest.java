package com.example.ClinicalSystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="requests")
public class PatientRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role = Role.PATIENT;
	
	@Column(name="name",columnDefinition="VARCHAR(40)")
	private String name;
  
	@Column(name="lastname",columnDefinition="VARCHAR(40)")
	private String lastname;
	
	@Column(name="email",columnDefinition="VARCHAR(50)", unique = true)
	private String email;
	
	@Column(name="password",columnDefinition="VARCHAR(30)")
	private String password;

	public PatientRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PatientRequest(Long id, Role role, String name, String lastname, String email, String password) {
		super();
		this.id = id;
		this.role = role;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
	
	
	
	

}

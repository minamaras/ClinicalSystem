package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Patient {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password" , nullable = false)
	private String password;
	
	@Column(name = "lastname" , nullable = false)
	private String lastname;
	
	@Column(name = "firstname" , nullable = false)
	private String firstname;
	
	@Column(name = "email" , nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<OldAppointment> oldAppointment = new HashSet<OldAppointment>();
	
	@OneToOne(mappedBy ="patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CurrentAppointment> currentAppointment = new HashSet<CurrentAppointment>();
	
	@OneToOne(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MedicalRecord medicalRecord;
		
	
}

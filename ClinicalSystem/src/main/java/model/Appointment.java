package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "date" , nullable = false)
	private String date;
	
	@Column(name = "time", nullable = false )
	private String time;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Patient patient;
		
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Doctor doctor;
	
	@Column(name = "hasHappend", nullable = false)
	private boolean hasHappend;
	
}

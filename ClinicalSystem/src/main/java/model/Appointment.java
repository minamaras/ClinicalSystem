package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	

}

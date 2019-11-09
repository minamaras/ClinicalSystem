package dto;

import model.Clinic;
import model.Doctor;

public class DoctorDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String rating;
	private Clinic clinic;
	
	public DoctorDTO() {
		super();
	}
	
	public DoctorDTO(Doctor doctor) {
		
		this(doctor.getId(), doctor.getName(), doctor.getLastname(), doctor.getRating(), doctor.getClinic(), doctor.getEmail());
	}
	
	public DoctorDTO(Long id, String firstName, String lastName, String rating, Clinic clinic, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rating = rating;
		this.clinic = clinic;
		this.email = email;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}

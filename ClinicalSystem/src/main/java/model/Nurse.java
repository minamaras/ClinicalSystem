package model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Nurse {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "firstName", nullable = false)
	String firstName;
	
	@Column(name = "lastName", nullable = false)
	String lastName;
	
	@Column(name = "username", nullable = false)
	String username;
	
	@Column(name = "password", nullable = false)
	String password;
	
	@Column(name = "email", nullable = false)
	String email;

	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Clinic clinic;

	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	ArrayList<Recipe> unathenticatedRecipes = new ArrayList<Recipe>();
	
	@OneToMany(mappedBy = "nurse", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	ArrayList<Recipe> athenticatedRecipes = new ArrayList<Recipe>();

	public Nurse() {
		
	}
	
	


	public Nurse(long id, String firstName, String lastName, String username, String password, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.athenticatedRecipes = new ArrayList<Recipe>();
		this.unathenticatedRecipes = new ArrayList<Recipe>();
		
	}




	public Nurse(long id, String firstName, String lastName, String username, String password, String email,
			Clinic clinic, ArrayList<Recipe> unathenticatedRecipes, ArrayList<Recipe> athenticatedRecipes) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.clinic = clinic;
		this.unathenticatedRecipes = unathenticatedRecipes;
		this.athenticatedRecipes = athenticatedRecipes;
	}



	public Clinic getClinic() {
		return clinic;
	}


	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	public ArrayList<Recipe> getUnathenticatedRecipes() {
		return unathenticatedRecipes;
	}

	public void setUnathenticatedRecipes(ArrayList<Recipe> unathenticatedRecipes) {
		this.unathenticatedRecipes = unathenticatedRecipes;
	}

	public ArrayList<Recipe> getAthenticatedRecipes() {
		return athenticatedRecipes;
	}

	public void setAthenticatedRecipes(ArrayList<Recipe> athenticatedRecipes) {
		this.athenticatedRecipes = athenticatedRecipes;
	}


	
	
	
	
}



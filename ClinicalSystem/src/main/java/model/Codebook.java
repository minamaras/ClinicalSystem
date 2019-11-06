package model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Codebook {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String id;
	
	@Column(name = "medication")
	ArrayList<String> medications = new ArrayList<String>();
	
	@Column(name = "diagnosis")
	ArrayList<String> diagnosis = new ArrayList<String>();
	
	public Codebook() {
		
	}

	public Codebook(String id) {
		this.id = id;
	}

	public Codebook(String id, ArrayList<String> medications, ArrayList<String> diagnosis) {
		super();
		this.id = id;
		this.medications = medications;
		this.diagnosis = diagnosis;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<String> getMedications() {
		return medications;
	}

	public void setMedications(ArrayList<String> medications) {
		this.medications = medications;
	}

	public ArrayList<String> getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(ArrayList<String> diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	
	

}

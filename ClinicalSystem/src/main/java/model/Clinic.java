package model;

import java.util.ArrayList;

public class Clinic {
	
	String id;
	String name;
	String adresa;
	String opis;
	String slobodniTermini;
	String cenovnik;
	ArrayList<Doctor> doctors = new ArrayList<Doctor>();
	
	Nurse nurse;
	Patient patient;
	

}

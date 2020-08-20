package com.example.ClinicalSystem.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ExamType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @Column(name = "price")
    private int price;

    @Column(name = "duration",nullable = false)
    private int duration;

    @OneToMany
    private Set<OR> rooms = new HashSet<OR>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Appointment> exams = new HashSet<Appointment>();

    public ExamType() {
        super();
    }

    public ExamType(String name, int price, int duration) {
        super();
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    public boolean doesExamTypeHaveAnyScheduledAppointments() { return this.getExams().isEmpty();}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Appointment> getExams() {
        return exams;
    }

    public void setExams(Set<Appointment> exams) {
        this.exams = exams;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<OR> getRooms() {
        return rooms;
    }

    public void setRooms(Set<OR> rooms) {
        this.rooms = rooms;
    }
}

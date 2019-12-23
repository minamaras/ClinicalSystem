package com.example.ClinicalSystem.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ExamRoom")
public class ExaminationRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "isreserved")
    private boolean isReserved;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "or", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Appointment> appointments = new HashSet<Appointment>();

    public ExaminationRoom() {
        super();
    }

    public ExaminationRoom(Long id, int number, boolean isReserved, String name) {
        super();
        this.id = id;
        this.number = number;
        this.isReserved = false;
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointment(Set<Appointment> appointments) {
        this.appointments = appointments;
    }


}

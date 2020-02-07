package com.example.ClinicalSystem.model;

import javax.persistence.*;

@Entity
public class Rating {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    double onevalue;


    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValue() {
        return onevalue;
    }

    public void setValue(double value) {
        this.onevalue = value;
    }
}

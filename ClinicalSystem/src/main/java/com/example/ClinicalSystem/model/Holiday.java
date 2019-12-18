package com.example.ClinicalSystem.model;

import javax.persistence.*;

@Entity
@Table(name="holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="type")
    private HolidayType type;

    public enum HolidayType {
        HOLIDAY,
        ABSENCE
    }

    @Column(name="reason")
    private String reason;

    public Holiday() {

    }
    
    public Holiday(HolidayType type, String reason){
        this.type = type;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HolidayType getType() {
        return type;
    }

    public void setType(HolidayType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

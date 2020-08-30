package com.example.ClinicalSystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="typeholiday")
    private HolidayType type;

    public enum HolidayType {
        Holiday,
        Absence
    }

    @Column(name="reason")
    private String reason;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    @Column(name="startdate")
    private Date start;

    @Column(name="enddate")
    private Date end;

    @Column(name = "email")
    private String email;

    @Column(name="holidaystatus")
    @Enumerated(EnumType.STRING)
    private HolidayRequestStatus holidayRequestStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

    public Holiday() {
    }

    public Holiday(HolidayType type, String reason, Date start, Date end, User user, String email, HolidayRequestStatus holidayRequestStatus){
        this.type = type;
        this.reason = reason;
        this.start = start;
        this.end = end;
        this.user = user;
        this.email = user.getEmail();
        this.holidayRequestStatus = holidayRequestStatus;
    }

    public boolean checkHolidaySpanValidity(Holiday holiday) {
        return holiday.getStart().compareTo(getStart()) == 0;
    }

    public boolean isOnHoliday() {
        return getHolidayRequestStatus() == HolidayRequestStatus.INPROGRESS;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HolidayRequestStatus getHolidayRequestStatus() {
        return holidayRequestStatus;
    }

    public void setHolidayRequestStatus(HolidayRequestStatus holidayRequestStatus) {
        this.holidayRequestStatus = holidayRequestStatus;
    }
}

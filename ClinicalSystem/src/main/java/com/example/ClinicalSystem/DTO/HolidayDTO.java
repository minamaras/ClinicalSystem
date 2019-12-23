package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.model.Nurse;

import java.util.Date;

public class HolidayDTO {

    private Long id;
    private Holiday.HolidayType type;
    private String reason;
    private Date start;
    private Date end;
    private String email;

    public HolidayDTO(Long id, Holiday.HolidayType type, String reason, Date start, Date end, String email){
        super();
        this.id = id;
        this.type = type;
        this.reason = reason;
        this.start = start;
        this.end = end;
        this.email = email;
    }

    public HolidayDTO(){
        super();
    }

    public HolidayDTO(Holiday holiday){
        this(holiday.getId(), holiday.getType(), holiday.getReason(), holiday.getStart(), holiday.getEnd(), holiday.getUser().getEmail());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Holiday.HolidayType getType() {
        return type;
    }

    public void setType(Holiday.HolidayType type) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

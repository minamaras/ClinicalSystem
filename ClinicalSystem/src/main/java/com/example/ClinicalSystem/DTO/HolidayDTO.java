package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.model.HolidayRequestStatus;
import com.example.ClinicalSystem.model.Nurse;

import java.util.Date;

public class HolidayDTO {

    private Long id;
    private Holiday.HolidayType type;
    private String reason;
    private Date start;
    private Date end;
    private String email;
    private HolidayRequestStatus holidayRequestStatus;
    private String fromto;
    private String startHoliday;
    private String endHoliday;

    public HolidayDTO(Long id, Holiday.HolidayType type, String reason, Date start, Date end, String email, HolidayRequestStatus holidayRequestStatus){
        super();
        this.id = id;
        this.type = type;
        this.reason = reason;
        this.start = start;
        this.end = end;
        this.email = email;
        this.holidayRequestStatus = holidayRequestStatus;
    }

    public HolidayDTO(){
        super();
    }

    public HolidayDTO(Holiday holiday){
        this(holiday.getId(), holiday.getType(), holiday.getReason(), holiday.getStart(), holiday.getEnd(), holiday.getEmail(), holiday.getHolidayRequestStatus());
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

    public HolidayRequestStatus getHolidayRequestStatus() {
        return holidayRequestStatus;
    }

    public void setHolidayRequestStatus(HolidayRequestStatus holidayRequestStatus) {
        this.holidayRequestStatus = holidayRequestStatus;
    }

    public String getFromto() {
        return fromto;
    }

    public void setFromto(String fromto) {
        this.fromto = fromto;
    }

    public String getStartHoliday() {
        return startHoliday;
    }

    public void setStartHoliday(String startHoliday) {
        this.startHoliday = startHoliday;
    }

    public String getEndHoliday() {
        return endHoliday;
    }

    public void setEndHoliday(String endHoliday) {
        this.endHoliday = endHoliday;
    }
}

package com.example.ClinicalSystem.DTO;





import java.sql.Date;
import java.util.logging.Filter;
import org.joda.time.LocalTime;

public class FilterDTO {

    private String filter;

    private String parameter;

    private String date;

    private org.joda.time.LocalTime startAppointmentFilter;

    private String examtype;

    private String time;

    public FilterDTO(String filter, String parameter) {
        this.filter = filter;
        this.parameter = parameter;
    }

    public FilterDTO(){

    }


    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    public LocalTime getStartAppointmentFilter() {
        return startAppointmentFilter;
    }

    public void setStartAppointmentFilter(LocalTime startAppointmentFilter) {
        this.startAppointmentFilter = startAppointmentFilter;
    }

    public String getExamtype() {
        return examtype;
    }

    public void setExamtype(String examtype) {
        this.examtype = examtype;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

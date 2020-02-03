package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.model.Nurse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NurseCalendarDTO {

    private String nurseemail;
    private String clinicName;
    private List<AppointmentDTO> appointments = new ArrayList<>();
    private Set<HolidayDTO> holidays = new HashSet<>();

    public NurseCalendarDTO(String nurseemail, String clinicName, List<AppointmentDTO> appointments, Set<HolidayDTO> holidays) {
        this.nurseemail = nurseemail;
        this.clinicName = clinicName;
        this.appointments = appointments;
        this.holidays = holidays;
    }

    public NurseCalendarDTO(Nurse nurse){
        this.nurseemail = nurse.getEmail();
        this.clinicName = nurse.getClinic().getName();
    }

    public NurseCalendarDTO(){
        super();
    }

    public String getNurseemail() {
        return nurseemail;
    }

    public void setNurseemail(String nurseemail) {
        this.nurseemail = nurseemail;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public List<AppointmentDTO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDTO> appointments) {
        this.appointments = appointments;
    }

    public Set<HolidayDTO> getHolidays() {
        return holidays;
    }

    public void setHolidays(Set<HolidayDTO> holidays) {
        this.holidays = holidays;
    }
}

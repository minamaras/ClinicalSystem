package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {

    AppointmentRequest save(AppointmentRequest a);
    Long removeById(Long id);
    AppointmentRequest findByName(String name);
    AppointmentRequest findByPatient(Patient p);
    AppointmentRequest findByStart(Date start);
    AppointmentRequest findByType(ExamType type);
}

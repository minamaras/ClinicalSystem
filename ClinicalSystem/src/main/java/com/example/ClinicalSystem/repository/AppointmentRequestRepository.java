package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {

    AppointmentRequest save(AppointmentRequest a);
    Long removeById(Long id);
    AppointmentRequest findByName(String name);
    List<AppointmentRequest> findByPatient(Patient p);
    AppointmentRequest findByStart(Date start);
    AppointmentRequest findByStartTime(Time starttime);
    AppointmentRequest findByType(ExamType type);
    Optional<AppointmentRequest> findById(Long id);
}

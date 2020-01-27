package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Appointment;
import com.example.ClinicalSystem.model.AppointmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {

    AppointmentRequest save(AppointmentRequest a);
    Long removeById(Long id);
    AppointmentRequest findByName(String name);
}

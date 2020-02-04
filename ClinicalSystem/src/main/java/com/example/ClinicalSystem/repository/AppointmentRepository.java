package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment save(Appointment a);
    Appointment findByName(String name);
    Appointment findById(long id);
}

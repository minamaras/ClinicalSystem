package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Medication save(Medication diagnosis);
    List<Medication> findAllByOrderByNameAsc();
    Medication findByName(String name);
}

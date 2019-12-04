package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    Diagnosis save(Diagnosis diagnosis);
    List<Diagnosis> findAll();
    Diagnosis findByName(String name);
}

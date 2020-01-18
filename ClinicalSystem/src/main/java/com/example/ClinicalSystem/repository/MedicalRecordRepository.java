package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findAll();
    MedicalRecord findById(int id);
}

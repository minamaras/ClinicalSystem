package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Holiday;
import com.example.ClinicalSystem.model.MedicalRecord;
import com.example.ClinicalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    MedicalRecord save(MedicalRecord medicalRecord);
    List<MedicalRecord> findAll();
    MedicalRecord findByPatient(Patient patient);
    MedicalRecord findById(long id);

}

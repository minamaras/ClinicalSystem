package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Clinic;
import com.example.ClinicalSystem.model.OR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRoomRepository extends JpaRepository<OR, Long> {

    OR save(OR or);
    OR findByNumber(int number);
    void deleteByNumber(int number);
    OR findById(long id);
    List<OR> findAllByClinic(Clinic clinic);
}

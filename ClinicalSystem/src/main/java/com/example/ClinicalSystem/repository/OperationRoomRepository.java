package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.OR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRoomRepository extends JpaRepository<OR, Long> {

    OR save(OR or);
}

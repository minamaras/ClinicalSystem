package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.ClinicalCentreAdmin;
import com.example.ClinicalSystem.model.OR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRoomRepository extends JpaRepository<OR, Long>  {
}

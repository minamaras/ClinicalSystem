package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {

    ExamType findByName(String name);
    ExamType deleteByName(String name);
}

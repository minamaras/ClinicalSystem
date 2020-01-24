package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamTypeRepository extends JpaRepository<ExamType, Long> {

    ExamType findByName(String name);
    void deleteByName(String name);
}

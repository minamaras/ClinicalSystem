package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report save(Report report);
    List<Report> findAll();
    Report findById(long id);

}

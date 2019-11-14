package com.example.ClinicalSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ClinicalSystem.model.Nurse;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {


	Nurse save(Nurse nurse);
}

package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
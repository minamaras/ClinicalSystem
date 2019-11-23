package com.example.ClinicalSystem.service.interfaces;

import com.example.ClinicalSystem.model.Authority;

import java.util.List;

public interface AuthorityServiceInterface {

    List<Authority> findById(Long id);
    List<Authority> findByname(String name);
}
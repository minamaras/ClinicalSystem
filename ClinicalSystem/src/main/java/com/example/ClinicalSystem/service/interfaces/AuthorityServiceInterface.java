package com.example.ClinicalSystem.service.interfaces;

import com.example.ClinicalSystem.model.Authority;

import java.util.List;

public interface AuthorityServiceInterface {

    Authority findById(Long id);
    Authority findByname(String name);
}
package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.Authority;
import com.example.ClinicalSystem.repository.AuthorityRepository;
import com.example.ClinicalSystem.service.interfaces.AuthorityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService implements AuthorityServiceInterface {

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findById(Long id) {
        Authority auth = this.authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByname(String name) {
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
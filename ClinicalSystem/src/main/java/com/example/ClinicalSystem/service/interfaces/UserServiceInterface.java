package com.example.ClinicalSystem.service.interfaces;

import com.example.ClinicalSystem.DTO.UserDTO;
import com.example.ClinicalSystem.model.PatientRequest;
import com.example.ClinicalSystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    Optional<User> findById(Long id);
    User findByUsername(String email);
    List<User> findAll ();

    boolean loginUser(UserDTO userDTO);
    boolean existsInDB(UserDTO userDTO);

}

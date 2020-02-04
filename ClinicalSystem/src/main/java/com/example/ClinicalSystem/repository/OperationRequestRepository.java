package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.OperationRequest;
import com.example.ClinicalSystem.model.PatientRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRequestRepository extends JpaRepository<OperationRequest, Long> {

    OperationRequest findById(long id);
    OperationRequest save(OperationRequest patientRequest);
    List<OperationRequest> findAll();

}

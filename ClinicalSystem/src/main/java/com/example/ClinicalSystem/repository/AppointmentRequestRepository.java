package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.AppointmentRequest;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from AppointmentRequest a")
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "0") })
    List<AppointmentRequest> findAll();

    AppointmentRequest save(AppointmentRequest a);
    Long removeById(Long id);
    AppointmentRequest findByName(String name);
    List<AppointmentRequest> findByPatient(Patient p);
    AppointmentRequest findByStart(Date start);
    AppointmentRequest findByStartTime(Time starttime);
    AppointmentRequest findByType(ExamType type);
    List<AppointmentRequest> findAllByOrderByStartAsc();


}

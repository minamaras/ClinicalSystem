package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.ExaminationRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExaminationRoomRepository extends JpaRepository<ExaminationRoom, Long> {

    ExaminationRoom save(ExaminationRoom room);
    ExaminationRoom findByNumber(int number);
    void deleteByNumber(int number);
}

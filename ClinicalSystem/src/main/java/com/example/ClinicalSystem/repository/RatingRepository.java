package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Rating save(Rating rating);

}

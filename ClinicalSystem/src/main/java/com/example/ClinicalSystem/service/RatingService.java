package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.Rating;
import com.example.ClinicalSystem.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating save(Rating rating){

        return  ratingRepository.save(rating);
    }
}

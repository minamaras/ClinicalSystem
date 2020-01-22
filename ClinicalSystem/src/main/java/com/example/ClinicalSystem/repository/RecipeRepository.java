package com.example.ClinicalSystem.repository;

import com.example.ClinicalSystem.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe save(Recipe recipe);
    List<Recipe> findAll();
    Optional<Recipe> findById(Long id);

}

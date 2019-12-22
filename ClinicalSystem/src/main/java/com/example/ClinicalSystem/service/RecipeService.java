package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.Recipe;
import com.example.ClinicalSystem.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe addRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

}

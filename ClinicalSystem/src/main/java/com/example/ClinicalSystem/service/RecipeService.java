package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.RecipeDTO;
import com.example.ClinicalSystem.model.ClinicAdmin;
import com.example.ClinicalSystem.model.Nurse;
import com.example.ClinicalSystem.model.Recipe;
import com.example.ClinicalSystem.model.User;
import com.example.ClinicalSystem.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  UserService userService;

    public Recipe addRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public List<RecipeDTO> findAll(){

        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipesDTO = new ArrayList<>();
        for (Recipe r : recipes) {
            recipesDTO.add(new RecipeDTO(r));
        }

        return recipesDTO;
    }

    public List<RecipeDTO> findUnauth(){

        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipesDTO = new ArrayList<>();
        for (Recipe r : recipes) {
            if(!r.isAuth()){
                recipesDTO.add(new RecipeDTO(r));
            }
        }

        return recipesDTO;
    }

    }

    public Optional<Recipe> findbyId(Long id){
        return recipeRepository.findById(id);
    }
}

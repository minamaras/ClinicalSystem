package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.RecipeDTO;
import com.example.ClinicalSystem.model.*;
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

    public RecipeDTO authRecipe(RecipeDTO recipeDTO, Principal p){

        User user = userService.findByUsername(p.getName());
        //recipeDTO.setNurseemail(user.getEmail());
        Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
        Doctor dr = (Doctor) userService.findByUsername(recipeDTO.getDoctoremail());
        Patient patient = (Patient) userService.findByUsername(recipeDTO.getPatientemail());
        recipe.setDoctor(dr);
        recipe.setPatient(patient);

        if(!recipe.isAuth()){
            recipe.setAuth(true);
            recipe.setNurse((Nurse) user);
            save(recipe);
        }

        RecipeDTO recipeDTO1 = modelMapper.map(recipe, RecipeDTO.class);
        recipeDTO1.setNurseemail(user.getEmail());
        recipeDTO1.setDoctoremail(recipe.getDoctor().getEmail());
        recipeDTO1.setPatientemail(recipe.getPatient().getEmail());
        recipeDTO1.setAuth(true);
        return recipeDTO1;
    }

    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findbyId(Long id){
        return recipeRepository.findById(id);
    }
}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


}

package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.RecipeDTO;
import com.example.ClinicalSystem.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;


    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {

        List<RecipeDTO> recipes = recipeService.findAll();

        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allunauth")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity<List<RecipeDTO>> getUnauthorisedRecipes() {

        List<RecipeDTO> recipes = recipeService.findUnauth();

        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authenticate")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity<?> authentificateRecipe(@RequestBody RecipeDTO recipeDTO, Principal p) {


        RecipeDTO recipeDTO1 = recipeService.authRecipe(recipeDTO, p);

        return new ResponseEntity<>(recipeDTO1, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/patients")
    @PreAuthorize("hasAnyAuthority('NURSE', 'DOCTOR')")
    public ResponseEntity<List<RecipeDTO>> getPatientsRecipes(@RequestBody String patientemail) {

        List<RecipeDTO> recipesdto = recipeService.findPatients(patientemail);

        return new ResponseEntity<>(recipesdto, HttpStatus.OK);
    }

}

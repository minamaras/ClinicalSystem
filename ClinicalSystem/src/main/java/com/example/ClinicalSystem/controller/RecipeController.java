package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.RecipeDTO;
import com.example.ClinicalSystem.DTO.ReportDTO;
import com.example.ClinicalSystem.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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

        List<RecipeDTO> recipes = recipeService.findAllDto();

        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/allunauth")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity<List<RecipeDTO>> getUnauthorisedRecipes() {

        List<RecipeDTO> recipes = recipeService.findUnauth();

        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/authenticate/{id}")
    @PreAuthorize("hasAuthority('NURSE')")
    public ResponseEntity authentificateRecipe(@PathVariable String id, Principal p) {


        boolean isAuth = recipeService.authRecipe(id, p);

        if(isAuth) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/patients")
    @PreAuthorize("hasAnyAuthority('NURSE', 'DOCTOR')")
    public ResponseEntity<List<RecipeDTO>> getPatientsRecipes(@RequestBody String patientemail) {

        List<RecipeDTO> recipesdto = recipeService.findPatients(patientemail);

        return new ResponseEntity<>(recipesdto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addrecipe")
    @PreAuthorize("hasAnyAuthority('DOCTOR','NURSE')")
    public ResponseEntity<?> addRecipe(@RequestBody RecipeDTO recipeDTO, Principal p) {

        boolean isAdded = recipeService.addNew(recipeDTO, p);
        if(!isAdded){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }


}

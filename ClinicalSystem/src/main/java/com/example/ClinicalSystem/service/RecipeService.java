package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.ClinicAdminDTO;
import com.example.ClinicalSystem.DTO.RecipeDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private MedicationService medicationService;

    public Recipe addRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public List<RecipeDTO> findAllDto(){

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


    public List<RecipeDTO> findPatients(String patientemail){
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeDTO> recipesDTO = new ArrayList<>();

        //Patient patient = patientService.findPatient(patientemail);
        //MedicalRecord medicalRecord = medicalRecordService.findById(patient.getMedicalRecord().getId());
        //List<Report> reports = new ArrayList<>();


        for(Recipe r : recipes){
            if(r.getPatient().getEmail().equals(patientemail)){
                RecipeDTO recipeDTO = new RecipeDTO(r);
                recipesDTO.add(recipeDTO);
            }
        }
        return recipesDTO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public boolean authRecipe(String id, Principal p){

        long idRec = Long.parseLong(id);

        Recipe recipe = recipeRepository.findById(idRec);

        User user = userService.findByUsername(p.getName());
        
        if(!recipe.isAuth()){
            recipe.setAuth(true);
            recipe.setNurse((Nurse) user);
            save(recipe);

            return true;
        }

        return false;
    }

   // @Transactional(propagation = Propagation.)
    public Recipe save(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findbyId(Long id){
        return recipeRepository.findById(id);
    }

    public Boolean addNew(RecipeDTO recipeDTO, Principal p){
        Doctor doctor = (Doctor) userService.findByUsername(p.getName());
        Patient patient = patientService.findPatient(recipeDTO.getPatientemail());

        if(patient == null){
            return false;
        }

        Recipe recipe = new Recipe();

        Set<Medication> medications = new HashSet<>();

        for(String med : recipeDTO.getMedicationName()){
            Medication medication = medicationService.findByName(med);
            medications.add(medication);
        }

        recipe.setPatient(patient);
        recipe.setDoctor(doctor);
        recipe.setContent(recipeDTO.getContent());
        recipe.setMedications(medications);

        save(recipe);
        return true;

    }
}

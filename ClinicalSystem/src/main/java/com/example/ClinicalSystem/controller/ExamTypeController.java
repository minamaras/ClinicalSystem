package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.ExamTypeDTO;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.service.ExamTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "api/examtypes")
public class ExamTypeController {

    @Autowired
    private ExamTypeService examTypeService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<List<ExamTypeDTO>> getAllTypes() {

        List<ExamTypeDTO> examDtos = examTypeService.findAll();

        return new ResponseEntity<>(examDtos, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savetype")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<ExamTypeDTO> saveType(@RequestBody ExamTypeDTO examTypeDTO) {

        if(examTypeService.saveType(examTypeDTO)) {
            return new ResponseEntity<>(examTypeDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

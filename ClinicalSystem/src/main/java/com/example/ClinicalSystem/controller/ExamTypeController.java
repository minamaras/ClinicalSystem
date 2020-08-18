package com.example.ClinicalSystem.controller;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.ExamTypeDTO;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.service.ExamTypeService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    @PreAuthorize("hasAnyAuthority('CLINICADMIN','PATIENT')")
    public ResponseEntity<List<ExamTypeDTO>> getAllTypes() {

        List<ExamTypeDTO> examDtos = examTypeService.findAllExamTypes();

        return new ResponseEntity<>(examDtos, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savetype")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<ExamTypeDTO> saveType(@RequestBody ExamTypeDTO examTypeDTO) {

        if(examTypeService.saveType(examTypeDTO)) {
            return new ResponseEntity<>(examTypeDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deletetype")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<Void> deleteType(@RequestBody ExamTypeDTO examTypeDTO) {

        if(examTypeService.deleteType(examTypeDTO)) {

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @PreAuthorize("hasAuthority('CLINICADMIN')")
    public ResponseEntity<ExamTypeDTO> updateType(@RequestBody ExamTypeDTO examTypeDTO) {

       if(examTypeService.findOne(examTypeDTO.getName()) == null)
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

       ExamType examType = examTypeService.findOne(examTypeDTO.getName());

       if(examTypeDTO.getPrice() >= 0)
           examType.setPrice(examTypeDTO.getPrice());

       examTypeService.updateType(examType);

       return new ResponseEntity<>(modelMapper.map(examType, ExamTypeDTO.class), HttpStatus.OK);
    }


}

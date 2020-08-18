package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.ExamTypeDTO;
import com.example.ClinicalSystem.model.Doctor;
import com.example.ClinicalSystem.model.ExamType;
import com.example.ClinicalSystem.model.OR;
import com.example.ClinicalSystem.repository.DoctorRepository;
import com.example.ClinicalSystem.repository.ExamTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamTypeService {

    @Autowired
    private ExamTypeRepository examTypeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OperationRoomService operationRoomService;
    @Autowired
    private DoctorRepository doctorRepository;

    public List<ExamTypeDTO> findAll() {
        List<ExamType> exams = examTypeRepository.findAll();

        List<ExamTypeDTO> examTypeDTOS = new ArrayList<>();
        for (ExamType e : exams) {
            examTypeDTOS.add(new ExamTypeDTO(e));
        }

        return examTypeDTOS;
    }

    public ExamType findOne(String name) {
        return examTypeRepository.findByName(name);
    }

    public boolean saveType(ExamTypeDTO examTypeDTO) {
        if((findOne(examTypeDTO.getName()) != null) || examTypeDTO.getPrice() <= 0) {
            return false;
        }

        ExamType examType = modelMapper.map(examTypeDTO, ExamType.class);

        examTypeRepository.save(examType);
        return true;
    }

    @Transactional
    public boolean deleteType(ExamTypeDTO examTypeDTO) {
        if(findOne(examTypeDTO.getName()) != null) {
            ExamType examType = modelMapper.map(examTypeDTO, ExamType.class);

            if(examType.getExams().isEmpty()) {
                List<OR> rooms = operationRoomService.findAllRoomsModel();

                for(OR r : rooms) {
                    if(r.getExamType().getName().equals(examType.getName()))
                        operationRoomService.removeOR(r);
                }

                examTypeRepository.deleteByName(examType.getName());
                return true;
            }
            return false;
        }
        return  false;
    }

    public ExamType updateType(ExamType examType) {
        return examTypeRepository.save(examType);
    }
}
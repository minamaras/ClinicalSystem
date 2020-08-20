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

    public List<ExamTypeDTO> findAllExamTypes() {
        List<ExamType> allExamTypes = examTypeRepository.findAll();

        List<ExamTypeDTO> allExamTypesDtos = convertToExamTypeDtoFromExamTypeModel(allExamTypes);
        return allExamTypesDtos;
    }

    private List<ExamTypeDTO> convertToExamTypeDtoFromExamTypeModel(List<ExamType> allExamTypes) {
        List<ExamTypeDTO> allExamTypesDtos = new ArrayList<>();
        for (ExamType examType : allExamTypes) allExamTypesDtos.add(new ExamTypeDTO(examType));
        return allExamTypesDtos;
    }

    public ExamType findExamTypeByItsName(String examTypeName) {
        return examTypeRepository.findByName(examTypeName);
    }

    public boolean saveExamType(ExamTypeDTO examTypeDTO) {
        ExamType examType = convertToExamTypeModelFromDto(examTypeDTO);

        if (canNewExamTypeBeCreated(examType)) return false;

        examTypeRepository.save(examType);
        return true;
    }

    private boolean canNewExamTypeBeCreated(ExamType examType) {
        return isThereExamTypeWithTheSameName(examType) || examType.isExamTypePriceNumberGreaterThanZero();
    }

    private ExamType convertToExamTypeModelFromDto(ExamTypeDTO examTypeDTO) {
        return modelMapper.map(examTypeDTO, ExamType.class);
    }

    private boolean isThereExamTypeWithTheSameName(ExamType examType) {
        return findExamTypeByItsName(examType.getName()) != null;
    }
    
    @Transactional
    public boolean deleteExamType(ExamTypeDTO examTypeDTO) {
        ExamType examType = convertToExamTypeModelFromDto(examTypeDTO);

        if (!isThereExamTypeWithTheSameName(examType)) {
            return  false;
        }

        if (examType.doesExamTypeHaveAnyScheduledAppointments()) return false;

        removeExamTypeFromAssociatedRooms(examType);
        examTypeRepository.deleteByName(examType.getName());
        return true;
    }

    private void removeExamTypeFromAssociatedRooms(ExamType examType) {
        List<OR> allRoomsInAClinic = operationRoomService.findAllRoomsModel();

        for(OR room : allRoomsInAClinic) {
            if(examType.examTypeIsAssociatedWithRoom(room))
                operationRoomService.removeOR(room);
        }
    }

    public ExamType updateExistingExamType(ExamType examType) {
        return examTypeRepository.save(examType);
    }
}
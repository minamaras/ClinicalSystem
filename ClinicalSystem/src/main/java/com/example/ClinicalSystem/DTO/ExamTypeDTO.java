package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ExamType;

public class ExamTypeDTO {

    String name;
    Long id;

    public ExamTypeDTO() {
        super();
    }

    public ExamTypeDTO(ExamType type) {
        this(type.getId(), type.getName());
    }

    public ExamTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.ExamType;

public class ExamTypeDTO {

    private String name;
    private Long id;
    private int price;
    private int duration;

    public ExamTypeDTO() {
        super();
    }

    public ExamTypeDTO(ExamType type) {
        this(type.getId(), type.getName(), type.getPrice(), type.getDuration());
    }

    public ExamTypeDTO(Long id, String name, int price, int duration) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.duration = duration;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}

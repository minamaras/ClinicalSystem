package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.OR;

import java.sql.Time;
import java.util.Date;

public class OperationRoomDTO {

    private Long id;
    private int number;
    private String name;
    private String examTypeName;
    private Date dateReserved;
    private Time start;
    private Time end;
    private ExamTypeDTO examType;

    public OperationRoomDTO(Long id, int number, String name, String examTypeName, Time start, Time end, ExamTypeDTO examType) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.examTypeName = examTypeName;
        this.dateReserved = dateReserved;
        this.start = start;
        this.end = end;
        this.examType = examType;
    }

    public OperationRoomDTO() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamTypeName() {
        return examTypeName;
    }

    public void setExamTypeName(String examTypeName) {
        this.examTypeName = examTypeName;
    }

    public Date getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(Date dateReserved) {
        this.dateReserved = dateReserved;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public ExamTypeDTO getExamType() {
        return examType;
    }

    public void setExamType(ExamTypeDTO examType) {
        this.examType = examType;
    }
}

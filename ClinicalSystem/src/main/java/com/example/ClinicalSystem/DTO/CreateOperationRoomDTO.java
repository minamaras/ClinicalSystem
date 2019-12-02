package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.OR;

public class CreateOperationRoomDTO {

    private int number;
    private boolean isReserved;
    private String name;

    public CreateOperationRoomDTO(OR or){
        this(or.getNumber(), or.getName(), or.isReserved());

    }

    public CreateOperationRoomDTO(int number, String name, boolean isReserved) {
        this.number = number;
        this.name = name;
        this.isReserved = isReserved;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

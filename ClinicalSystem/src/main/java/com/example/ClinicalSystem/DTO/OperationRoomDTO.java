package com.example.ClinicalSystem.DTO;

import com.example.ClinicalSystem.model.OR;

public class OperationRoomDTO {

    private Long id;
    private int number;
    private String name;
    private boolean isReserved;

    public OperationRoomDTO(Long id, int number, String name, boolean isReserved) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.isReserved = isReserved;
    }

    public OperationRoomDTO() {
        super();
        this.isReserved = false;
    }

    public OperationRoomDTO(OR room) {
        this(room.getId(), room.getNumber(), room.getName(), room.isReserved());
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}

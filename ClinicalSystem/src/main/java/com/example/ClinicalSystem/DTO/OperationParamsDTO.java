package com.example.ClinicalSystem.DTO;

public class OperationParamsDTO {

    private long requestId;
    private String dateOperation;
    private String timeOperation;
    private int roomOperation;

    public OperationParamsDTO(long requestId, String dateOperation, String timeOperation, int roomOperation) {
        this.requestId = requestId;
        this.dateOperation = dateOperation;
        this.timeOperation = timeOperation;
        this.roomOperation = roomOperation;
    }

    public OperationParamsDTO(){
        super();
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getTimeOperation() {
        return timeOperation;
    }

    public void setTimeOperation(String timeOperation) {
        this.timeOperation = timeOperation;
    }

    public int getRoomOperation() {
        return roomOperation;
    }

    public void setRoomOperation(int roomOperation) {
        this.roomOperation = roomOperation;
    }
}

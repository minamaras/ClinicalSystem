package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.DoctorDTO;
import com.example.ClinicalSystem.DTO.OperationParamsDTO;
import com.example.ClinicalSystem.DTO.OperationRequestDTO;
import com.example.ClinicalSystem.DTO.OperationRoomDTO;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.OperationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
public class OperationRequestService {


    @Autowired
    private OperationRequestRepository operationRequestRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private OperationRoomService operationRoomService;

    @Autowired
    private EmailService emailService;

    public OperationRequest findOne(long id){
        return operationRequestRepository.findById(id);
    }

    public List<OperationRequestDTO> allRequests(){
        List<OperationRequest> operationRequests = operationRequestRepository.findAll();
        List<OperationRequestDTO> operationRequestDTOS = new ArrayList<>();

        for(OperationRequest or : operationRequests){
            if(!or.isScheduled()) {
                OperationRequestDTO orDTO = new OperationRequestDTO(or);
                orDTO.setStart(or.getStart().toString().substring(0,10));
                orDTO.setStartTime(or.getStartTime());
                orDTO.setEndTime(or.getEndTime());
                operationRequestDTOS.add(orDTO);
            }
        }
        return operationRequestDTOS;
    }

    public OperationRequestDTO getOne(long id){
        OperationRequest or = operationRequestRepository.findById(id);

        OperationRequestDTO oprDTO = new OperationRequestDTO(or);
        oprDTO.setStart(or.getStart().toString().substring(0,10));
        oprDTO.setStartTime(or.getStartTime());
        oprDTO.setEndTime(or.getEndTime());

        return oprDTO;
    }

    public OperationRequest save(OperationRequest operationRequest){
       return operationRequestRepository.save(operationRequest);
    }


    public boolean scheduleOperation(OperationRequestDTO operationRequestDTO) throws InterruptedException {

        OperationRequest operation = operationRequestRepository.findById(operationRequestDTO.getId());

        if(operation == null){
            return false;
        }

        if(operation.isScheduled()){
            return true;
        }

        java.util.Date originalDate = operation.getStart();

        String inputDateString;
        if(operationRequestDTO.getStart() == null) {
            inputDateString = operationRequestDTO.getDate();
        } else {
            inputDateString = operationRequestDTO.getStart();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate inputDate = LocalDate.parse(inputDateString);
        java.sql.Date finalDate = java.sql.Date.valueOf(inputDate);

        LocalTime startTime = operation.getStartTime().toLocalTime();
        LocalTime endTime = startTime.plusHours(2);
        Time finalStartTime = Time.valueOf(startTime);
        Time finalEndTime = Time.valueOf(endTime);

        String thisD = finalDate.toString().substring(0,10);
        String starts = finalStartTime.toString();

        operation.setStart(finalDate);
        operation.setStartTime(finalStartTime);
        operation.setEndTime(finalEndTime);



        OR oroom = operationRoomService.findOne(operationRequestDTO.getRoomNumber());
        operation.setOr(oroom);
        //save(operation);


        oroom.getOperations().add(operation);



        for(String name : operationRequestDTO.getDoctorNames()){
            Doctor doctor = doctorService.findOne(name);
          //  if(!operation.getDoctors().contains(doctor)) {
                operation.getDoctors().add(doctor);
                emailService.sendOperationInfoDoctor(doctor, thisD, starts);
                //doctor.getOperations().add(operation);
          //  }
        }

        operation.setScheduled(true);
        save(operation);
        operationRoomService.save(oroom);

        Patient patient = operation.getPatient();

        if(originalDate.compareTo(finalDate) > 0 ||  originalDate.compareTo(finalDate) < 0){

            emailService.sendOperationDateChange(patient,thisD,starts);
        } else {

            emailService.sendOperationInfo(patient,thisD,starts);
        }



        return true;

    }



    public void assignOperationAutomatically() throws ParseException, InterruptedException {
        List<OperationRequest> operations = operationRequestRepository.findAll();

        List<OperationRequest> notChecked = new ArrayList<>();
        for (OperationRequest op : operations){
            if(!op.isScheduled()){
                notChecked.add(op);
            }
        }

        if(!notChecked.isEmpty()) {

            for (OperationRequest operation : notChecked) {

                if (operation.isScheduled()) {
                    return;
                }

                Random random = new Random();
                //int minDay = (int) new Date().getTime();
                int minDay = (int) LocalDate.of(2020, 1, 1).toEpochDay();
                int maxDay = (int) LocalDate.of(2021, 1, 1).toEpochDay();
                long randomDay = minDay + random.nextInt(maxDay - minDay);

                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
                java.util.Date finalDate = java.sql.Date.valueOf(randomDate);
                String thisD = finalDate.toString().substring(0, 10);


                OperationRequestDTO operationRequestDTO = new OperationRequestDTO();
                operationRequestDTO.setId(operation.getId());
                operationRequestDTO.setDate(thisD);
                operationRequestDTO.setStartTime(operation.getStartTime());
                operationRequestDTO.setEndTime(operation.getEndTime());

                List<OperationRoomDTO> operationRoomDTOS = operationRoomService.allRoomsForOperationRandom(finalDate, operation.getStartTime(), operation.getEndTime());

                int randomRoom = (int) (Math.random() * operationRoomDTOS.size());
                int roomNum = operationRoomDTOS.get(randomRoom).getNumber();
                OperationParamsDTO operationParamsDTO = new OperationParamsDTO(operation.getId(), thisD, operation.getStartTime().toString(), roomNum);

                List<DoctorDTO> freeDoctors = doctorService.getFreeDoctorsForOperation(operationParamsDTO);

                operationRequestDTO.setRoomNumber(roomNum);

                List<String> docNames = new ArrayList<>();
                for (DoctorDTO doc : freeDoctors) {
                    docNames.add(doc.getEmail());
                }
                operationRequestDTO.setDoctorNames(docNames);

                scheduleOperation(operationRequestDTO);


            }
        }

    }

}

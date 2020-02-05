package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.DTO.*;
import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.repository.OperationRoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.security.Principal;
import java.text.ParseException;
import java.util.*;


@Service
public class OperationRoomService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OperationRoomRepository repo;

    @Autowired
    private ClinicService clinicService;

    @Autowired
    private UserService userService;

    @Autowired
    private ClinicAdminService clinicAdminService;

    @Autowired
    private AppointmentRequestService appointmentRequestService;

    @Autowired
    private ExamTypeService examTypeService;

    public List<OperationRoomDTO> findAll() {

        List<OR> rooms = repo.findAll();

        List<OperationRoomDTO> roomsDTO = new ArrayList<>();
        for (OR r : rooms) {
            roomsDTO.add(modelMapper.map(r, OperationRoomDTO.class));
        }

        return roomsDTO;
    }

    public boolean save(OperationRoomDTO roomDto, Principal p) {

        ClinicAdmin cAdmin = (ClinicAdmin) userService.findByUsername(p.getName());
        Clinic clinic = cAdmin.getClinic();

        OR room = modelMapper.map(roomDto, OR.class);

        if(repo.findByNumber(roomDto.getNumber()) != null) {

            return false;
        }

        if(clinic != null) {
            room.setClinic(clinic);
            clinic.getRooms().add(room);
            roomDto.setClinicname(clinic.getName());
            roomDto.setClinicid(clinic.getId());
        }

        ExamType  examType = examTypeService.findOne(roomDto.getExamType().getName());
        room.setExamType(examType);
        ExamTypeDTO examTypeDTO = modelMapper.map(examType, ExamTypeDTO.class);
        roomDto.setExamType(examTypeDTO);
        repo.save(room);
        return true;
    }

    @Transactional
    public boolean removeRoom(OperationRoomDTO roomDTO) {

        if(repo.findByNumber(roomDTO.getNumber()) != null) {

            OR room = modelMapper.map(roomDTO, OR.class);

            if(room.getAppointments().isEmpty()) {
                repo.deleteByNumber(room.getNumber());
                return true;
            }
        }

        return false;

    }

    public OR findOne(int number) {
        return repo.findByNumber(number);
    }

    public OR update(OR or) {
        return repo.save(or);
    }

    public Set<OperationRoomDTO> findAllRoomsFromAClinic(String clinicname) throws ParseException {


        List<AppointmentRequestDTO> waitingreq = appointmentRequestService.findAllWaiting();

        HashSet<OperationRoomDTO> roomsToReturn = new HashSet<>();
        Clinic clinic = null;

        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) aut.getPrincipal();

        if(user.getRole().equals(Role.CLINICADMIN)){
            ClinicAdmin ca = clinicAdminService.findByEmail(user.getEmail());
            clinic = clinicService.findClinic(ca.getClinic());
        }

        List<OR> rooms = repo.findAllByClinic(clinic);
        Set<Long> setOfIds = new HashSet<>();
        for(OR room : rooms){
            setOfIds.add(room.getId());
            OperationRoomDTO operationRoomDTO = modelMapper.map(room, OperationRoomDTO.class);
            ExamTypeDTO examTypeDTO = modelMapper.map(room.getExamType(),ExamTypeDTO.class);
            operationRoomDTO.setClinicid(clinic.getId());
            operationRoomDTO.setExamType(examTypeDTO);
            Set<AppointmentDTO> appointmentDTOS = new HashSet<>();

            for(Appointment a : room.getAppointments()){
                AppointmentDTO appointmentDTO = modelMapper.map(a,AppointmentDTO.class);
                appointmentDTO.setDate(a.getStart().toString().substring(0,10));
                appointmentDTO.setStartTime(a.getStartTime());
                appointmentDTO.setEndTime(a.getEndTime());
                appointmentDTOS.add(appointmentDTO);

            }
            List<AppointmentRequestDTO> roomrequests = new ArrayList<>();

            for(AppointmentRequestDTO apr : waitingreq)
            {
                        if(room.getId() == apr.getRoomId()){
                            apr.setDate(apr.getStart().toString().substring(0,10));
                            roomrequests.add(apr);
                    }

            }

            operationRoomDTO.setAppointmentRequests(roomrequests);
            operationRoomDTO.setAppointments(appointmentDTOS);
            roomsToReturn.add(operationRoomDTO);

        }

        return  roomsToReturn;
    }

    public void saveModel(OR or){
        repo.save(or);
    }

    public OperationRoomDTO findById(Long id){
        Optional<OR> ap =repo.findById(id);
        return modelMapper.map(ap.get(),OperationRoomDTO.class);
    }

    public OR findByIdModel(Long id){
        Optional<OR> ap =repo.findById(id);
        return ap.get();
    }

}

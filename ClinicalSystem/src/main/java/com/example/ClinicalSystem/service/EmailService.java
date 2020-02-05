package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private Environment env;

    @Autowired
    private PatientService patientService;

    @Autowired
    private HolidayService holidayService;

    @Async
    public void sendDeclineNotificaitionAsync(User user, String explanation) throws MailException, InterruptedException {


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Registration");
        mail.setText("Hello " + user.getName() + ",\n\nYour request for registration on our website has been denied. Here's an explanation:\n\n" + explanation + "\n\n\nClinical System Team");
        javaMailSender.send(mail);

    }

    @Async
    public void sendRejectedHolidayAsync(User user) throws MailException, InterruptedException {


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Holiday/Absence Request");
        mail.setText("Hello " + user.getName() + ",\n\nYour request for holiday/absence has been denied. Here's an explanation:\n\n" + "\n\n\nClinical System Team");
        javaMailSender.send(mail);

    }

    @Async
    public void sendAcceptNotificaitionAsync(Patient patient) throws MailException, InterruptedException {

        //ConfirmationToken confirmationToken = new ConfirmationToken(patient);
        //confirmationTokenService.save(confirmationToken);
        patient.setVerificationCode(UUID.randomUUID().toString());
        patientService.updatePatient(patient);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(patient.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Confirm account");
        mail.setText("Hello " + patient.getName() + ",\n\nYour request for registration has been accepted. Click the following link to activate your account:\n\n" + "http://localhost:8081/api/patients/confirm-account/" + patient.getVerificationCode() + "\n\n\nClinical System Team");
        javaMailSender.send(mail);

    }

    @Async
    public void sendConfirmHolidayAsync(User user) throws MailException, InterruptedException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Holiday Request");
        mail.setText("Hello " + user.getName() + ",\n\nYour request for holiday/absence has been accepted." + "\n\n\nClinical System Team");
        javaMailSender.send(mail);

    }


    @Async
    public void sendAdminNotificaitionAsync(User user, Patient patient, Date date, Time starttime, Time endtime, ExamType type) throws MailException, InterruptedException {


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Appointment request");
        mail.setText("Hello " + user.getName() + ",\n\nYou have a new  appointment request from patient "+patient.getName()+" "+patient.getLastname() +" for appointment type "+type.getName()+" "+date +" "+" at "+starttime+ "\n\n\n Clinical System");
        javaMailSender.send(mail);

    }


    public void sendAppointmentRequest(Patient patient, String examdate, String examtime, String endtime) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(patient.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Appointment request");
        mail.setText("Hello " + patient.getName() + ",\n\n Please confirm or decline this appointment request. Appointment is on "+ examdate +" from  "+ examtime +" till "+ endtime +" . Click the following link: "+ "http://localhost:3000/patientrequeststatus" + "\n\n\n Clinical System");
        javaMailSender.send(mail);
    }

    public void sendDoctorRequest(Doctor doctor, Patient patient, String examdate, String examtime, String endtime, Long idRequest) {
        Clinic clinic = doctor.getClinic();
        List<ClinicAdmin> admins = new ArrayList<>();
        for(ClinicAdmin ca : clinic.getClinicAdmins()){
            admins.add(ca);
        }

        int randomNumberAdmin = (int)(Math.random() * admins.size());
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(admins.get(randomNumberAdmin).getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Appointment request");
        mail.setText("Hello " + admins.get(randomNumberAdmin).getName() + ",\n\n Please assign a room for this appointment request. Appointment is on "+ examdate +" from  "+ examtime +" till "+ endtime +" " + "\n\n\n Clinical System");
        javaMailSender.send(mail);
    }
}
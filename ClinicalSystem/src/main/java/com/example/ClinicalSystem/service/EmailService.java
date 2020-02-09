package com.example.ClinicalSystem.service;

import com.example.ClinicalSystem.model.*;
import com.example.ClinicalSystem.DTO.AppointmentRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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


    public void sendAppointmentRequest(Patient patient, String examdate, String examtime, String endtime, AppointmentRequestDTO apdto) throws MessagingException {

        /*SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(patient.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Appointment request");
        mail.setText("Hello " + patient.getName() + ",\n\n Please confirm or decline this appointment request. Appointment is on "+ examdate +" from  "+ examtime +" till "+ endtime +" . Click the following link to accept: "+ "<html> <a href='http://localhost:8081/saverequesttoappointment'"+"></a></html>" + "\n\n\n Clinical System");
        javaMailSender.send(mail);*/

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg,true);
        helper.setTo(patient.getEmail());

        String accept = "http://localhost:8081/api/appointments/saverequesttoappointment/";
        String decline = "http://localhost:8081/api/appointments/declinerequesttoappointment/";

        String content = "Hello " + patient.getName() + ",\n\n Please confirm or decline this appointment request. Appointment is on "+ examdate +" from  "+ examtime +" till "+ endtime +" ." +
                "<a href="+accept+apdto.getId()+">Click here</a>"+"to confirm this request. To decline request click"+"<a href="+decline+apdto.getId()+">Click here</a>"+
                "\n\n\n Clinical System";
        helper.setText(content,true);

        javaMailSender.send(msg);
    }


    @Async
    public void sendEmailAboutAppointment(User p, Appointment a) throws MailException, InterruptedException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(p.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Scheduled appointment");
        mail.setText("Hello " + p.getName() + ",\n\nYour successfully scheduled predefined appointment. Here is some information about you upcoming appointment: " +
                "\n Appointment date " + a.getStart().toString().substring(0, 10) +
                "\n Appointment time " + a.getStartTime() +
                "\n Appointment type " + a.getType().getName() +
                "\n Appointment or " + a.getOr().getName() +
                "\n Appointment doctor " + a.getDoctor().getName() + " " + a.getDoctor().getLastname() +
                "\n\n\nClinical System Team");


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


    @Async
    public void sendOperationDateChange(Patient patient, String opDate, String opTime) throws MailException, InterruptedException {


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(patient.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Operation date changed");
        mail.setText("Hello " + patient.getName() + ",\n\nYour operation date is changed and it is scheduled on " + opDate + " at " + opTime + "." + "\n\n\n Clinical System");
        javaMailSender.send(mail);

    }

    @Async
    public void sendOperationInfo(Patient patient, String opDate, String opTime) throws MailException, InterruptedException {


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(patient.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Operation information");
        mail.setText("Hello " + patient.getName() + ",\n\nYour operation is scheduled on " + opDate + " at " + opTime + "." + "\n\n\n Clinical System");
        javaMailSender.send(mail);

    }



    @Async
    public void sendOperationInfoDoctor(Doctor doctor, String opDate, String opTime) throws MailException, InterruptedException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(doctor.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Clinical System: Operation");
        mail.setText("Hello " + doctor.getName() + ",\n\nYou have been charged for operation. It starts on " + opDate + " at " + opTime + "." + "\n\n\n Clinical System");
        javaMailSender.send(mail);

    }

}
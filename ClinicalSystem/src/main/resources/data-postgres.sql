insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-6,'admin', 'admin', 'admin@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICALCENTREADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-5,'jelena', 'bojanic', 'jelena.bojanic97@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'PATIENT','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-4,'mina', 'maras', 'mina.maras@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'DOCTOR','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-3,'tamara', 'jancic', 'tamara@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-8,'sestra', 'sestr', 'sestra@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'NURSE','2019-12-01 09:00:01');

INSERT INTO clinic (id, description, name, adress,rating) values (-3, 'Klinika broj 1', 'Klinika1', 'Bulevar Oslobodjenja 22','9');
insert into clinic_admin (id,clinic_id) values (-3,-3);
insert into doctor (id,specialization,rating,clinic_id,clinic_admin_id) values (-4,'zubar','10',-3,-3);
insert into clinical_centre_admin (id) values (-6);
insert into nurse (id, clinic_id) values (-8, -3);


insert into patient (id,active,adress,city,country,phone,socialSecurityNumber) values (-5,'true','Bulevar Oslobodjenja 11','Novi Sad','Srbija','064335512','0301997805089');

insert into recipe (id, is_authenthicated, content, doctor_id, nurse_id, patient_id) values (-121, false, 'prvi recept', -4, null, -5);
insert into recipe (id, is_authenthicated, content, doctor_id, nurse_id, patient_id) values (-131, false, 'drugi recept', -4, null, -5);
insert into recipe (id, is_authenthicated, content, doctor_id, nurse_id, patient_id) values (-141, false, 'treci recept', -4, null, -5);


insert into clinic_admin_doctors(clinic_admin_id,doctors_id) values(-3,-4);
insert into clinic_doctors (clinic_id,doctors_id) values (-3,-4);
insert into clinic_nurses (clinic_id,nurses_id) values (-3,-8);


INSERT INTO authority (id,name) values (1,'PATIENT');
INSERT INTO authority (id,name) values (2,'DOCTOR');
INSERT INTO authority (id,name) values (3,'NURSE');
INSERT INTO authority (id,name) values (4,'CLINICADMIN');
INSERT INTO authority (id,name) values (5,'CLINICALCENTREADMIN');

INSERT INTO user_authority (user_id,authority_id) values (-6,5);
INSERT INTO user_authority (user_id,authority_id) values (-5,1);
INSERT INTO user_authority (user_id,authority_id) values (-4,2);
INSERT INTO user_authority (user_id,authority_id) values (-3,4);
INSERT INTO user_authority (user_id,authority_id) values (-8,3);
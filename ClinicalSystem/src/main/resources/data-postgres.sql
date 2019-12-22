insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-6,'admin', 'admin', 'admin@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICALCENTREADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-5,'jelena', 'bojanic', 'jelena.bojanic97@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'PATIENT','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-4,'mina', 'maras', 'mina.maras@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'DOCTOR','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-3,'tamara', 'jancic', 'tamara@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-8,'sestra', 'sestr', 'sestra@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'NURSE','2019-12-01 09:00:01');

insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-9,'biljana', 'petrovic', 'biljana@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-10,'sandra', 'babic', 'sandra@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICADMIN','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-2,'olivera', 'stojanovic', 'olivera@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICADMIN','2019-12-01 09:00:01');

insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-7,'doktor1', 'doktor1', 'doktor1@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'DOCTOR','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-8,'doktor2', 'doktor2', 'doktor2@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'DOCTOR','2019-12-01 09:00:01');
insert into users (id,name, lastname, email, password, role,last_password_reset_date) values (-11,'doktor3', 'doktor3', 'doktor3@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'DOCTOR','2019-12-01 09:00:01');


INSERT INTO clinic (id, description, name, adress,rating) values (-3, 'Klinika broj 1', 'Klinika1', 'Bulevar Oslobodjenja 22','9');
INSERT INTO clinic (id, description, name, adress,rating) values (-5, 'Klinika broj 2', 'Klinika2', 'Njegoseva 3','6');
INSERT INTO clinic (id, description, name, adress,rating) values (-7, 'Klinika broj 3', 'Klinika3', 'Safarikova 11','7');
INSERT INTO clinic (id, description, name, adress,rating) values (-2, 'Klinika broj 4', 'Klinika4', 'Bulevar Evrope 8','10');

insert into clinic_admin (id,clinic_id) values (-9,-5);
insert into clinic_admin (id,clinic_id) values (-10,-7);
insert into clinic_admin (id,clinic_id) values (-2,-2);

insert into clinic_admin (id,clinic_id) values (-3,-3);
insert into exam_type (id,name,price) values (-4,'Stomatoloski pregled',1500);

insert into doctor (id,specialization,rating,clinic_id,clinic_admin_id,exam_type_id) values (-4,'zubar','10',-3,-3,-4);

insert into doctor (id,specialization,rating,clinic_id,clinic_admin_id,exam_type_id) values (-7,'zubar','10',-5,-9,-4);
insert into doctor (id,specialization,rating,clinic_id,clinic_admin_id,exam_type_id) values (-8,'kardiolog','9',-7,-10,-4);
insert into doctor (id,specialization,rating,clinic_id,clinic_admin_id,exam_type_id) values (-11,'ginekolog','7',-2,-2,-4);


insert into clinical_centre_admin (id) values (-6);
insert into exam_type_doctors (exam_type_id,doctors_id) values (-4,-4);
insert into nurse (id, clinic_id) values (-8, -3);

insert into patient (id,active,adress,city,country,phone,socialSecurityNumber) values (-5,'true','Bulevar Oslobodjenja 11','Novi Sad','Srbija','064335512','0301997805089');

insert into clinic_admin_doctors(clinic_admin_id,doctors_id) values(-3,-4);

insert into clinic_admin_doctors(clinic_admin_id,doctors_id) values(-9,-7);
insert into clinic_admin_doctors(clinic_admin_id,doctors_id) values(-10,-8);
insert into clinic_admin_doctors(clinic_admin_id,doctors_id) values(-2,-11);


insert into clinic_doctors (clinic_id,doctors_id) values (-3,-4);
insert into clinic_nurses (clinic_id,nurses_id) values (-3,-8);

insert into clinic_doctors (clinic_id,doctors_id) values (-5,-7);
insert into clinic_doctors (clinic_id,doctors_id) values (-7,-8);
insert into clinic_doctors (clinic_id,doctors_id) values (-2,-11);

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

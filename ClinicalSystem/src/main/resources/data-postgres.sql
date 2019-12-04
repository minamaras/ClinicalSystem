insert into users (name, lastname, email, password, role,last_password_reset_date) values ('admin', 'admin', 'admin@gmail.com', '$2a$10$En99NVAv.YrTtVxJ1fssBeVO4AFnfl1OMwzFbPeaDdSBm1KLUzp12', 'CLINICALCENTREADMIN','2019-12-01 09:00:01');

INSERT INTO authority (id,name) values (1,'PATIENT');
INSERT INTO authority (id,name) values (2,'DOCTOR');
INSERT INTO authority (id,name) values (3,'NURSE');
INSERT INTO authority (id,name) values (4,'CLINICADMIN');
INSERT INTO authority (id,name) values (5,'CLINICALCENTREADMIN');

INSERT INTO user_authority (user_id,authority_id) values (1,5)



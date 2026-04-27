/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  anonymous
 * Created: 27 Apr 2026
 */




CREATE TABLE IF NOT EXISTS Patient (
                id INT NOT NULL AUTO_INCREMENT,
                name VARCHAR(50) NOT NULL,
                email VARCHAR(50) NOT NULL,
                pword VARCHAR(128) NOT NULL,
                PRIMARY KEY (id),  
                UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS Clinic (
				id INT NOT NULL AUTO_INCREMENT,
				name varchar(64) not null,
				address varchar(256) not null,
				PRIMARY KEY (id),
				UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS Service(
			id int not null auto_increment,
			name varchar(128) not null,
			description varchar(256) not null,
			clinic_id int,
			hidden bool default false,
			PRIMARY KEY (id),			
			FOREIGN KEY (clinic_id) REFERENCES Clinic(id),
                        UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS ClinicService(
				id int not null auto_increment,
				clinic_id int,
				service_id int,
				primary key (id),
				foreign key (clinic_id) references Clinic(id),
				foreign key (service_id) references Service(id),
				unique (clinic_id, service_id)
);

CREATE TABLE IF NOT EXISTS Appointment (
                id INT NOT NULL AUTO_INCREMENT,
                date DATETIME NOT NULL,
                status ENUM('AWAITING','CANCELLED_CLINIC','CANCELLED_USER','MISSED','DONE','CONFIRMED') NOT NULL,
                patientId INT NOT NULL,
                clinicId INT NOT NULL,
                serviceId INT NOT NULL,
                PRIMARY KEY (id),
                FOREIGN KEY (patientId) REFERENCES Patient(id),           
                FOREIGN KEY (clinicId) REFERENCES Clinic(id),
                FOREIGN KEY (serviceId) REFERENCES Service(id)
);

CREATE TABLE IF NOT EXISTS Employee (
				id int not null auto_increment,
				role varchar(64) not null,
				name varchar(128) not null,
				email varchar(128) not null,
				password varchar(128) not null,
				clinic_id int,
				PRIMARY KEY (id),
				UNIQUE (email),
				FOREIGN KEY (clinic_id) REFERENCES Clinic(id)
);


CREATE TABLE IF NOT EXISTS Queue (
                id INT NOT NULL AUTO_INCREMENT,
                clinicId INT NOT NULL,
                serviceId INT NOT NULL,
                capacity INT NOT NULL,
                PRIMARY KEY (id), 
                FOREIGN KEY (clinicId) REFERENCES Clinic(id),
                FOREIGN KEY (serviceId) REFERENCES Service(id)
);
        

CREATE TABLE IF NOT EXISTS PatientQueue (
                id INT NOT NULL AUTO_INCREMENT,
                patientId INT NOT NULL,
                queueId INT NOT NULL,         
                PRIMARY KEY (id),
                FOREIGN KEY (patientId) REFERENCES Patient(id),
                FOREIGN KEY (queueId) REFERENCES Queue(id)
);   

CREATE TABLE IF NOT EXISTS TimeSlot(
				id int not null auto_increment,
				start time not null,
				end time not null,
				capacity int not null,
				primary key (id),
				unique (start, end)
);

CREATE TABLE IF NOT EXISTS ClinicTimeSlot(
                id INT NOT NULL AUTO_INCREMENT,
                clinicId INT NOT NULL,
                timeslotId INT NOT NULL,
		capacity int not null default 0,
                PRIMARY KEY (id),
                FOREIGN KEY (clinicId) REFERENCES Clinic(id),
                FOREIGN KEY (timeslotId) REFERENCES TimeSlot(id),
		UNIQUE (clinicId, timeslotId)
);

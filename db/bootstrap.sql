-- just in case we need it
create table if not exists Clinic(
	id int not null auto_increment,
	name varchar(64) not null,
	address varchar(256) not null,
	primary key (id),
	unique (name)
);

create table if not exists Employee(
	id int not null auto_increment,
	role varchar(64) not null,
	name varchar(128) not null,
	email varchar(128) not null,
	password varchar(128) not null,
	clinic_id int,
	primary key (id),
	unique (email),
	foreign key (clinic_id) references Clinic(id)
);

create table if not exists Service(
	id int not null auto_increment,
	name varchar(128) not null,
	description varchar(256) not null,
	clinic_id int,
	primary key (id),
	unique (name),
	foreign key (clinic_id) references Clinic(id)
);

create table if not exists ClinicService(
	id int not null auto_increment,
	clinic_id int,
	service_id int,
	primary key (id),
	foreign key (clinic_id) references Clinic(id),
	foreign key (service_id) references Service(id)
);

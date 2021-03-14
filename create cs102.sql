create schema CS102;
use CS102;

create table vessel
( abbrvslm varchar (100) not null primary key,
  fullvslm varchar (100) not null
 );
 
 create table berth
 ( berthnum int not null primary key
 );
 
 create table user(
  username varchar(50) not null primary key,
  email varchar(50) not null,
  password varchar(64) not null,
  role varchar(45) not null,
  enabled tinyint(4) default null
);
 
create table voyage(
abbrvslm varchar(100) not null,
inVoyN varchar (8) not null,
fullinvoyn varchar(12) not null,
outvoyn varchar(12) not null,
btrDt datetime not null,
unbthgDt datetime not null,
berthnum int not null,
status varchar(20) not null,
constraint voyage_pk primary key (abbrvslm, invoyn),
constraint voyage_fk1 foreign key(abbrvslm) references vessel(abbrvslm),
constraint voyage_fk2 foreign key (berthnum) references berth(berthnum)
);

 
create table voyagedetails(
	id int not null auto_increment,
    avg_speed float(5,2),
    distance_to_go float (8,2),
    max_speed float (5,2),
    is_patching_activated int,
    patching_predicted_btr datetime,
    predicted_btr datetime,
    abbrvslm varchar(100) not null,
    invoyn varchar(8) not null,
    vslvoy varchar(108) not null,
    constraint voyagedetails primary key (id, abbrvslm, invoyn),
    constraint voyagedetails foreign key (abbrvslm, invoyn) references voyage (abbrvslm, invoyn)
);





create table alert(
 alerttype varchar(30) not null,
 alertcontent varchar (100) not null,
 alertdatetime datetime not null,
 invoyn int not null,
 abbrvsim varchar(100) not null,
 id int not null,
 constraint alert_pk primary key(id, abbrvslm, invoyn),
 constraint alert_fk1 foreign key(id, abbrvslm, invoyn) references voyagedetails(id, abbrvslm, invoyn)
);

create table berthpreference(
username varchar(50) not null,
berthnum int not null,
constraint berthpreference_pk primary key (username, berthnum),
constraint berthpreference_fk1 foreign key (username) references user(username),
constraint berthpreference_fk2 foreign key (berthnum) references berth(berthnum)
);

create table vesselpreference(
username varchar(50) not null,
abbrvslm varchar (100) not null,
constraint vesselpreference_pk primary key (username, abbrvslm),
constraint vesselpreference_fk1 foreign key (username) references user(username),
constraint vesselpreference_fk2 foreign key (abbrvslm) references vessel(abbrvslm)
);

create table userpreference(
username varchar(50) not null,
abbrvslm varchar(100) not null,
berthnum int not null,
constraint userpreference_pk primary key (username, abbrvslm),
constraint userpreference_fk1 foreign key (username) references user(username),
constraint userpreference_fk2 foreign key (abbrvslm) references vessel(abbrvslm),
constraint userpreference_fk3 foreign key (berthnum) references berth(berthnum)
);

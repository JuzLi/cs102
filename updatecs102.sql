create schema CS102;
use CS102;

create table vessel
( abbrvslm varchar (100) not null primary key,
  fullvslm varchar (100) not null
 );
 
 create table berth
 ( berthnum varchar(10) not null primary key
 );
 
 create table user(
  username varchar(50) not null primary key,
  email varchar(50) not null,
  password varchar(64) not null,
  role varchar(45) not null,
  enabled tinyint(4) default null
);
 
create table voyage(
#voyageid int not null primary key auto_increment,
abbrvslm varchar(100) not null,
inVoyN varchar (8) not null,
fullinvoyn varchar(12),
outvoyn varchar(12) not null,
btrDt datetime,
unbthgDt datetime,
berthnum varchar(10),
status varchar(20) not null,
constraint voyage_pk primary key (abbrvslm, invoyn),
constraint voyage_fk1 foreign key(abbrvslm) references vessel(abbrvslm),
constraint voyage_fk2 foreign key (berthnum) references berth(berthnum)
);

 
create table voyagedetails(
	voyagedetailsid int not null,
    avg_speed float(5,2),
    distance_to_go float (8,2),
    max_speed float (5,2),
    is_patching_activated int,
    patching_predicted_btr datetime,
    predicted_btr datetime,
    abbrvslm varchar(100) not null,
    invoyn varchar(8) not null,
    vslvoy varchar(108) not null,
    constraint voyagedetails_pk primary key (abbrvslm, invoyn, voyagedetailsid),
    constraint voyagedetails_fk foreign key (abbrvslm,invoyn) references voyage (abbrvslm,invoyn)
);





create table alert(
 alertCount int not null,
 alerttype varchar(30) not null,
 alertcontent varchar (100) not null,
 alertdatetime datetime not null,
 invoyn varchar (8) not null,
 abbrvslm varchar(100) not null,
 constraint alert_pk primary key (abbrvslm,invoyn, alertCount, alerttype),
 constraint alert_fk1 foreign key(abbrvslm,invoyn) references voyage(abbrvslm,invoyn)
);

create table alertpreference(
username varchar(50) not null,
alerttype varchar (30) not null,
constraint vesselpreference_pk primary key (username, alerttype),
constraint vesselpreference_fk1 foreign key (username) references user(username)
);

create table vesselpreference(
username varchar(50) not null,
abbrvslm varchar (100) not null,
constraint vesselpreference_pk primary key (username, abbrvslm),
constraint vesselpreference_fk1 foreign key (username) references user(username),
constraint vesselpreference_fk2 foreign key (abbrvslm) references vessel(abbrvslm)
);

-- create table userpreference(
-- upid int not null primary key auto_increment,
-- username varchar(50) not null,
-- abbrvslm varchar(100) not null,
-- berthnum varchar(10) not null,
-- constraint userpreference_fk1 foreign key (username) references user(username),
-- constraint userpreference_fk2 foreign key (abbrvslm) references vessel(abbrvslm),
-- constraint userpreference_fk3 foreign key (berthnum) references berth(berthnum)
-- );

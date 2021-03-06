create schema CS102;
use CS102;

create table vessel
( abbrvsim varchar (100) not null primary key,
  fullvsim varchar (100) not null,
  vesselname varchar(100) not null
 );
 
 create table berth
 ( berthnum int not null primary key
 );
 
 create table berthvessel
 ( constraint berthvessel_pk primary key(abbrvsim,berthnum),
   constraint berthsessel_fk1 foreign key (abbrvsim) references vessel(abbrvsim),
   constraint berthVessel_fk2 foreign key (berthnum) references berth(berthnum),
   abbrvsim varchar (100) not null,
   berthnum int not null,
   btrdt datetime not null,
   unbthgdt datetime not null
);
 
  create table voyageref
 ( outvoynum int not null primary key,
   fullinvoynum int
 );
 
 create table voyage
 ( constraint voyage_pk primary key(outvoyNum, abbrvsim),
   constraint voyage_fk1 foreign key(outvoynum) references voyageref(outvoynum),
   constraint voyage_fk2 foreign key(abbrvsim) references vessel(abbrvsim),
   avgspeed decimal (5,2) not null,
   maxspeed decimal (5,2) not null,
   outvoynum int not null,
   abbrvsim varchar(100) not null,
   invoynum int not null,
   Status varchar(20) not null,
   distancetogo int not null,
   ispatchingactivated bool not null,
   patchingpredictedbtr datetime not null,
   predictedbtr datetime not null,
   voyagecodeinbound varchar(20) not null,
   vslvoy varchar(100) not null
 );

create table user(
  username varchar(50) not null primary key,
  email varchar(50) not null,
  password varchar(64) not null,
  role varchar(45) not null,
  enabled tinyint(4) default null
);



create table alert(
 alerttype varchar(30) not null,
 alertcontent varchar (100) not null,
 alertdatetime datetime not null,
 outvoynum int not null,
 abbrvsim varchar(100) not null,
 constraint alert_pk primary key(outvoynum,abbrvsim, alertdatetime),
 constraint alert_fk1 foreign key(outvoynum,abbrvsim) references voyage(outvoynum, abbrvsim)
);

create table preferences(
 preferencetype varchar(30) not null,
 username varchar(50) not null,
 alertdatetime datetime not null,
 outvoynum int not null,
 abbrvsim varchar(100) not null,
 constraint preferences_pk primary key(outvoynum,abbrvsim, alertdatetime, username),
 constraint preferences_fk1 foreign key(outvoynum,abbrvsim,alertdatetime) references alert(outvoynum,abbrvsim,alertdatetime),
 constraint preferences_fk2 foreign key(username) references user(username)
 );



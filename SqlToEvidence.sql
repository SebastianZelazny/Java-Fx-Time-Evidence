create database EvidenceJobTime;

use EvidenceJobTime;

create table logins (
ID_L int unique auto_increment primary key,
Login varchar(30) unique,
pass varchar(20),
role varchar(10)
);

create table employee(
ID_Emplo int unique auto_increment primary key,
ID_Login int unique,
FirstName varchar(30),
LastName varchar(30),
MaterialPrepare varchar(5),
scholing varchar(5),
delegation varchar(5),
foreign key (ID_Login) references Logins(ID_L)
);
#create table Evidence(
#ID_Evidence int unique auto_increment primary key,
#);
drop table logins;

insert into logins (Login,pass,role) values ('emplo1','emplo1','emp'),
											('emplo2','emplo2','emp'),
											('user1','user1','user'),
											('user2','user2','user'),
											('user3','user3','user');

select * from logins;
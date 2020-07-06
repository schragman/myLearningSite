alter table HAUPTTHEMA add Users_Id int not null;

ALTER TABLE HAUPTTHEMA ADD CONSTRAINT HauptThema_USERS_FK FOREIGN KEY (Users_Id) references USERS (USERID);

alter table HAUPTTHEMA drop foreign key HAUPTTHEMA_USERS_FK;
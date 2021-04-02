alter table USERS add constraint USERS_pk primary key (USERID);

alter table USERS drop primary key;

alter table USERS drop column USERID;

alter table USERS add constraint USERS_pk primary key (USERNAME);

drop index HAUPTTHEMA_USERS_FK on HAUPTTHEMA;

alter table HAUPTTHEMA modify Users_Id varchar(255) null;

alter table HAUPTTHEMA add constraint HAUPTTHEMA_USERS_FK foreign key (Users_Id) references USERS (USERNAME);


-- Ab Version 1.1
alter table USERS add Config_Id bigint null;

ALTER TABLE USERS ADD CONSTRAINT USERS_Konfiguration_FK FOREIGN KEY (Config_Id) references KONFIGURATION (ID);

-- Ab Version 1.2
alter table USERS
	add LABEL_ID int default 1 not null;
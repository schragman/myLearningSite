alter table HAUPTTHEMA add Users_Id int not null;

ALTER TABLE HAUPTTHEMA ADD CONSTRAINT HauptThema_USERS_FK FOREIGN KEY (Users_Id) references USERS (USERNAME);

alter table HAUPTTHEMA drop foreign key HAUPTTHEMA_USERS_FK;

alter table HAUPTTHEMA add Category_Id int not null;

alter table HAUPTTHEMA
	add constraint HAUPTTHEMA_CATEGORY_ID_fk
		foreign key (Category_Id) references CATEGORY (ID);
alter table HAUPTTHEMA add Users_Id int not null;

ALTER TABLE HAUPTTHEMA ADD CONSTRAINT HauptThema_USERS_FK FOREIGN KEY (Users_Id) references USERS (USERNAME);

alter table HAUPTTHEMA drop foreign key HAUPTTHEMA_USERS_FK;

--Ab Version 1.1
alter table HAUPTTHEMA add Category_Id int not null;

UPDATE HAUPTTHEMA set Category_Id=1 where users_id='schragman@gmail.com';

alter table HAUPTTHEMA add constraint HAUPTTHEMA_CATEGORY_ID_FK	FOREIGN KEY (Category_Id) references CATEGORY (ID);

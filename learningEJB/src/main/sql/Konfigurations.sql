create table KONFIGURATION
(
    ID            bigint               not null        primary key,
    DECRROWS      int                  not null,
    SAMPLEROWS    int                  not null
);

alter table USERS add Config_Id bigint null;
ALTER TABLE USERS ADD CONSTRAINT USERS_Konfiguration_FK FOREIGN KEY (Config_Id) references KONFIGURATION (ID);

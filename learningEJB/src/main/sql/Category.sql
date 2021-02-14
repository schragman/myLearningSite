create table CATEGORY
(
    ID    int         not null
        primary key,
    NAME  varchar(50) not null,
    THEMA bigint      not null,
    constraint CATEGORY_HAUPTTHEMA_ID_FK
        foreign key (THEMA) references HAUPTTHEMA (ID)
);
-- Ab Version 1.1
create table CATEGORY
(
    ID    int         not null
        primary key,
    NAME  varchar(50) not null,
    Users_Id varchar(255) not null,
    constraint CATEGORY_USERS_USERNAME_fk
        foreign key (Users_Id) references USERS (USERNAME)
);

INSERT INTO CATEGORY (ID, NAME, Users_Id) VALUES (1, 'Hauptkategorie','schragman@gmail.com');
alter table HISTORIE drop foreign key FK_HISTORIE_HISTORIEN_ID;
alter table HISTORIE drop column HISTORIEN_ID;
alter table ABFRAGE drop column LETZTESMALGESTELLT;
alter table ABFRAGE drop column LETZTESMALRICHTIG;
alter table ABFRAGE add REPETITIONRATE int not null;
alter table ABFRAGE add REPETITIONCOUNTER int not null;
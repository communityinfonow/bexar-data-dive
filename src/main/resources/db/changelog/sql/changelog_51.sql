--liquibase formatted sql

--changeset herronrb:data_corrections_es

alter table tbl_data_corrections rename column note to note_en;
alter table tbl_data_corrections add column note_es text;
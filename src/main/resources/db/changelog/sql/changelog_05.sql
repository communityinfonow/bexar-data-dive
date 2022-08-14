--liquibase formatted sql

--changeset herronrb:indicators4

alter table tbl_indicators add display boolean not null default true;
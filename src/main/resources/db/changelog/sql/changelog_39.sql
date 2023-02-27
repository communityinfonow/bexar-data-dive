--liquibase formatted sql

--changeset herronrb:survey_1

create table tbl_visitor_survey (
	date_ timestamp default current_timestamp,
	question text not null,
	response text not null,
	lang text not null,
	ip_address text not null
);
--liquibase formatted sql

--changeset herronrb:admin_5

create table tbl_announcements (
	id_ smallint generated always as identity primary key,
	title_en text,
	title_es text,
	message_en text,
	message_es text,
	date_ date,
	display boolean default false
);
--liquibase formatted sql

--changeset herronrb:data_corrections

create table if not exists tbl_data_corrections (
	id_ smallint generated always as identity primary key,
	indicator_id numeric references tbl_indicators,
	date_corrected date not null,
	years text[] not null,
	location_type_ids numeric[] not null,
	filter_type_ids numeric[] not null,
	display boolean not null default true
);
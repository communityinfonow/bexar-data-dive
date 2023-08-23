--liquibase formatted sql

--changeset herronrb:food_insecurity_prep_1

--insert into tbl_location_types (id_, name_en, name_es, sort_order) 
--values (8, 'Places', 'Lugares', 8);

alter table tbl_indicators add show_points boolean not null default false;
alter table tbl_indicators add show_report boolean not null default false;

update tbl_indicators set show_points = true where id_ = 77;
update tbl_indicators set show_report = true where id_ = 77;

create table tbl_point_types (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null,
	sort_order numeric not null,
	color text not null
);

insert into tbl_point_types (id_, name_en, name_es, sort_order, color) values
(1, 'School Nutrition Programs', 'School Nutrition Programs (es)', 1, 'red'),
(2, 'Summer Meal Programs', 'Summer Meal Programs (es)', 2, 'purple');

create table tbl_points (
	id_ text primary key,
	point_type_id numeric not null references tbl_point_types,
	year_ text not null,
	geojson json not null,
	value_ numeric
);

create table tbl_location_report (
	location_id text not null,
	location_type_id numeric not null,
	year_ text not null,
	report_data json not null
);
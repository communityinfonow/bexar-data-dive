--liquibase formatted sql

--changeset herronrb:aggregation_prep_4

create table tbl_custom_locations (
	id_ text primary key,
	name_ text,
	location_type_id numeric references tbl_location_types,
	location_ids text[],
	geojson json
);
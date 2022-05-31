--liquibase formatted sql

--changeset herronrb:1
create schema omh;

create table omh.tbl_location_types (
	id_ numeric primary key,
	name_en text,
	name_es text,
	sort_order numeric not null unique
);

create table omh.tbl_locations (
	id_ numeric primary key,
	geoid text not null,
	location_type_id numeric not null references omh.tbl_location_types,
	name_en text,
	name_es text,
	unique(geoid, location_type_id)
);

create table omh.tbl_indicator_categories (
	id_ numeric primary key,
	name_en text,
	name_es text,
	sort_order numeric not null unique
);

create table omh.tbl_indicators (
	id_ numeric primary key,
	indicator_category_id numeric not null references omh.tbl_indicator_categories,
	name_en text,
	name_es text,
	description_en text,
	description_es text
);

--changeset herronrb:2
insert into omh.tbl_location_types (id_, name_en, name_es, sort_order)
values (1, 'County', 'County (es)', 1), 
	(2, 'Super Neighborhood', 'Super Neighborhood (es)', 2), 
	(3, 'ZIP Code', 'ZIP Code (es)', 3),
	(4, 'Census Tract', 'Census Tract (es)', 4);

insert into omh.tbl_locations(id_, geoid, location_type_id, name_en, name_es)
values (1, '1', 1, 'Bexar County', 'Bexar County (es)'),
	(2, '2', 2, 'Super Neigborhood 1', 'Super Neigborhood 1 (es)'),
	(3, '3', 3, 'ZIP 1', 'ZIP 1 (es)'),
	(4, '4', 4, 'Tract 1', 'Tract 1 (es)'),
	(5, '5', 2, 'Super Neigborhood 2', 'Super Neigborhood 2 (es)'),
	(6, '6', 3, 'ZIP 2', 'ZIP 2 (es)'),
	(7, '7', 4, 'Tract 2', 'Tract 2 (es)');;

insert into omh.tbl_indicator_categories (id_, name_en, name_es, sort_order)
values (1, 'Category 1', 'Category 1 (es)', 1), 
	(2, 'Category 2', 'Category 2 (es)', 2), 
	(3, 'Category 3', 'Category 3 (es)', 3);


insert into omh.tbl_indicators (id_, indicator_category_id, name_en, name_es, description_en, description_es)
values (1, 1, 'Indicator 1', 'Indicator 1 (es)', 'Indicator 1 is...', 'Indicator 1 is...(es)'),
	(2, 1, 'Indicator 2', 'Indicator 2 (es)', 'Indicator 2 is...', 'Indicator 2 is...(es)'),
	(3, 2, 'Indicator 3', 'Indicator 3 (es)', 'Indicator 3 is...', 'Indicator 3 is...(es)'),
	(4, 2, 'Indicator 4', 'Indicator 4 (es)', 'Indicator 4 is...', 'Indicator 4 is...(es)'),
	(5, 3, 'Indicator 5', 'Indicator 5 (es)', 'Indicator 5 is...', 'Indicator 5 is...(es)'),
	(6, 3, 'Indicator 6', 'Indicator 6 (es)', 'Indicator 6 is...', 'Indicator 6 is...(es)');

--liquibase formatted sql

--changeset herronrb:1
create table tbl_location_types (
	id_ numeric primary key,
	name_en text,
	name_es text,
	sort_order numeric not null unique
);

create table tbl_locations (
	id_ numeric not null,
	location_type_id numeric not null references tbl_location_types,
	name_en text,
	name_es text,
	primary key(id_, location_type_id)
);

create table tbl_filter_types (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null
);

create table tbl_filter_options (
	id_ numeric primary key,
	type_id numeric not null references tbl_filter_types,
	name_en text not null,
	name_es text not null,
	sort_order numeric not null
);

create table tbl_sources (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null,
	url_ text
);

create table tbl_indicator_categories (
	id_ numeric primary key,
	name_en text not null,
	name_es text not null,
	sort_order numeric not null unique
);

create table tbl_indicator_types (
	id_ numeric primary key,
	name_ text not null
);

create table tbl_indicators (
	id_ numeric primary key,
	indicator_category_id numeric not null references tbl_indicator_categories,
	indicator_type_id numeric not null references tbl_indicator_types,
	name_en text not null,
	name_es text not null,
	description_en text not null,
	description_es text not null,
	base_filter_type_id numeric references tbl_filter_types
);

create table tbl_indicator_values (
	indicator_id numeric not null references tbl_indicators,
	year_ text not null,
	source_id numeric not null references tbl_sources,
	location_id numeric not null,
	location_type_id numeric not null,
	indicator_value numeric,
	moe_low numeric,
	moe_high numeric,
	universe_value numeric,
	race_id numeric references tbl_filter_options,
	age_id numeric references tbl_filter_options,
	sex_id numeric references tbl_filter_options,
	education_id numeric references tbl_filter_options,
	income_id numeric references tbl_filter_options,
	foreign key(location_id, location_type_id) references tbl_locations(id_, location_type_id)
);
create unique index idx_indicator_values on tbl_indicator_values (indicator_id, year_, source_id, location_id, location_type_id, race_id, age_id, sex_id, education_id, income_id);

--changeset herronrb:2
insert into tbl_location_types (id_, name_en, name_es, sort_order)
values (1, 'Council Districts', 'Council Districts (es)', 1), 
	(2, 'Zip Codes', 'Zip Codes (es)', 2), 
	(3, 'SSA''s', 'SSA''s (es)', 3),
	(4, 'Census Tracts', 'Census Tracts (es)', 4);

insert into tbl_locations(id_, location_type_id) values (1,1);
insert into tbl_locations(id_, location_type_id) values (2,1);
insert into tbl_locations(id_, location_type_id) values (3,1);
insert into tbl_locations(id_, location_type_id) values (4,1);
insert into tbl_locations(id_, location_type_id) values (5,1);
insert into tbl_locations(id_, location_type_id) values (6,1);
insert into tbl_locations(id_, location_type_id) values (7,1);
insert into tbl_locations(id_, location_type_id) values (8,1);
insert into tbl_locations(id_, location_type_id) values (9,1);
insert into tbl_locations(id_, location_type_id) values (10,1);
insert into tbl_locations(id_, location_type_id) values (78002,2);
insert into tbl_locations(id_, location_type_id) values (78015,2);
insert into tbl_locations(id_, location_type_id) values (78023,2);
insert into tbl_locations(id_, location_type_id) values (78069,2);
insert into tbl_locations(id_, location_type_id) values (78073,2);
insert into tbl_locations(id_, location_type_id) values (78101,2);
insert into tbl_locations(id_, location_type_id) values (78109,2);
insert into tbl_locations(id_, location_type_id) values (78112,2);
insert into tbl_locations(id_, location_type_id) values (78148,2);
insert into tbl_locations(id_, location_type_id) values (78150,2);
insert into tbl_locations(id_, location_type_id) values (78152,2);
insert into tbl_locations(id_, location_type_id) values (78154,2);
insert into tbl_locations(id_, location_type_id) values (78201,2);
insert into tbl_locations(id_, location_type_id) values (78202,2);
insert into tbl_locations(id_, location_type_id) values (78203,2);
insert into tbl_locations(id_, location_type_id) values (78204,2);
insert into tbl_locations(id_, location_type_id) values (78205,2);
insert into tbl_locations(id_, location_type_id) values (78207,2);
insert into tbl_locations(id_, location_type_id) values (78208,2);
insert into tbl_locations(id_, location_type_id) values (78209,2);
insert into tbl_locations(id_, location_type_id) values (78210,2);
insert into tbl_locations(id_, location_type_id) values (78211,2);
insert into tbl_locations(id_, location_type_id) values (78212,2);
insert into tbl_locations(id_, location_type_id) values (78213,2);
insert into tbl_locations(id_, location_type_id) values (78214,2);
insert into tbl_locations(id_, location_type_id) values (78215,2);
insert into tbl_locations(id_, location_type_id) values (78216,2);
insert into tbl_locations(id_, location_type_id) values (78217,2);
insert into tbl_locations(id_, location_type_id) values (78218,2);
insert into tbl_locations(id_, location_type_id) values (78219,2);
insert into tbl_locations(id_, location_type_id) values (78220,2);
insert into tbl_locations(id_, location_type_id) values (78221,2);
insert into tbl_locations(id_, location_type_id) values (78222,2);
insert into tbl_locations(id_, location_type_id) values (78223,2);
insert into tbl_locations(id_, location_type_id) values (78224,2);
insert into tbl_locations(id_, location_type_id) values (78225,2);
insert into tbl_locations(id_, location_type_id) values (78226,2);
insert into tbl_locations(id_, location_type_id) values (78227,2);
insert into tbl_locations(id_, location_type_id) values (78228,2);
insert into tbl_locations(id_, location_type_id) values (78229,2);
insert into tbl_locations(id_, location_type_id) values (78230,2);
insert into tbl_locations(id_, location_type_id) values (78231,2);
insert into tbl_locations(id_, location_type_id) values (78232,2);
insert into tbl_locations(id_, location_type_id) values (78233,2);
insert into tbl_locations(id_, location_type_id) values (78234,2);
insert into tbl_locations(id_, location_type_id) values (78235,2);
insert into tbl_locations(id_, location_type_id) values (78236,2);
insert into tbl_locations(id_, location_type_id) values (78237,2);
insert into tbl_locations(id_, location_type_id) values (78238,2);
insert into tbl_locations(id_, location_type_id) values (78239,2);
insert into tbl_locations(id_, location_type_id) values (78240,2);
insert into tbl_locations(id_, location_type_id) values (78242,2);
insert into tbl_locations(id_, location_type_id) values (78243,2);
insert into tbl_locations(id_, location_type_id) values (78244,2);
insert into tbl_locations(id_, location_type_id) values (78245,2);
insert into tbl_locations(id_, location_type_id) values (78247,2);
insert into tbl_locations(id_, location_type_id) values (78248,2);
insert into tbl_locations(id_, location_type_id) values (78249,2);
insert into tbl_locations(id_, location_type_id) values (78250,2);
insert into tbl_locations(id_, location_type_id) values (78251,2);
insert into tbl_locations(id_, location_type_id) values (78252,2);
insert into tbl_locations(id_, location_type_id) values (78253,2);
insert into tbl_locations(id_, location_type_id) values (78254,2);
insert into tbl_locations(id_, location_type_id) values (78255,2);
insert into tbl_locations(id_, location_type_id) values (78256,2);
insert into tbl_locations(id_, location_type_id) values (78257,2);
insert into tbl_locations(id_, location_type_id) values (78258,2);
insert into tbl_locations(id_, location_type_id) values (78259,2);
insert into tbl_locations(id_, location_type_id) values (78260,2);
insert into tbl_locations(id_, location_type_id) values (78261,2);
insert into tbl_locations(id_, location_type_id) values (78263,2);
insert into tbl_locations(id_, location_type_id) values (78264,2);
insert into tbl_locations(id_, location_type_id) values (78266,2);
insert into tbl_locations(id_, location_type_id) values (1,3);
insert into tbl_locations(id_, location_type_id) values (2,3);
insert into tbl_locations(id_, location_type_id) values (3,3);
insert into tbl_locations(id_, location_type_id) values (4,3);
insert into tbl_locations(id_, location_type_id) values (5,3);
insert into tbl_locations(id_, location_type_id) values (6,3);
insert into tbl_locations(id_, location_type_id) values (7,3);
insert into tbl_locations(id_, location_type_id) values (8,3);
insert into tbl_locations(id_, location_type_id) values (9,3);
insert into tbl_locations(id_, location_type_id) values (10,3);
insert into tbl_locations(id_, location_type_id) values (11,3);
insert into tbl_locations(id_, location_type_id) values (12,3);
insert into tbl_locations(id_, location_type_id) values (13,3);
insert into tbl_locations(id_, location_type_id) values (14,3);
insert into tbl_locations(id_, location_type_id) values (15,3);
insert into tbl_locations(id_, location_type_id) values (16,3);
insert into tbl_locations(id_, location_type_id) values (17,3);
insert into tbl_locations(id_, location_type_id) values (18,3);
insert into tbl_locations(id_, location_type_id) values (19,3);
insert into tbl_locations(id_, location_type_id) values (20,3);
insert into tbl_locations(id_, location_type_id) values (21,3);
insert into tbl_locations(id_, location_type_id) values (22,3);
insert into tbl_locations(id_, location_type_id) values (23,3);
insert into tbl_locations(id_, location_type_id) values (24,3);
insert into tbl_locations(id_, location_type_id) values (25,3);
insert into tbl_locations(id_, location_type_id) values (26,3);
insert into tbl_locations(id_, location_type_id) values (27,3);
insert into tbl_locations(id_, location_type_id) values (28,3);
insert into tbl_locations(id_, location_type_id) values (29,3);
insert into tbl_locations(id_, location_type_id) values (30,3);
insert into tbl_locations(id_, location_type_id) values (31,3);
insert into tbl_locations(id_, location_type_id) values (32,3);
insert into tbl_locations(id_, location_type_id) values (33,3);
insert into tbl_locations(id_, location_type_id) values (34,3);
insert into tbl_locations(id_, location_type_id) values (35,3);
insert into tbl_locations(id_, location_type_id) values (36,3);
insert into tbl_locations(id_, location_type_id) values (37,3);
insert into tbl_locations(id_, location_type_id) values (38,3);
insert into tbl_locations(id_, location_type_id) values (39,3);
insert into tbl_locations(id_, location_type_id) values (40,3);
insert into tbl_locations(id_, location_type_id) values (41,3);
insert into tbl_locations(id_, location_type_id) values (42,3);
insert into tbl_locations(id_, location_type_id) values (43,3);
insert into tbl_locations(id_, location_type_id) values (44,3);
insert into tbl_locations(id_, location_type_id) values (45,3);
insert into tbl_locations(id_, location_type_id) values (46,3);
insert into tbl_locations(id_, location_type_id) values (47,3);
insert into tbl_locations(id_, location_type_id) values (48,3);
insert into tbl_locations(id_, location_type_id) values (49,3);
insert into tbl_locations(id_, location_type_id) values (50,3);
insert into tbl_locations(id_, location_type_id) values (51,3);
insert into tbl_locations(id_, location_type_id) values (52,3);
insert into tbl_locations(id_, location_type_id) values (53,3);
insert into tbl_locations(id_, location_type_id) values (54,3);
insert into tbl_locations(id_, location_type_id) values (55,3);
insert into tbl_locations(id_, location_type_id) values (56,3);
insert into tbl_locations(id_, location_type_id) values (57,3);
insert into tbl_locations(id_, location_type_id) values (58,3);
insert into tbl_locations(id_, location_type_id) values (59,3);
insert into tbl_locations(id_, location_type_id) values (60,3);
insert into tbl_locations(id_, location_type_id) values (61,3);
insert into tbl_locations(id_, location_type_id) values (62,3);
insert into tbl_locations(id_, location_type_id) values (63,3);
insert into tbl_locations(id_, location_type_id) values (64,3);
insert into tbl_locations(id_, location_type_id) values (65,3);
insert into tbl_locations(id_, location_type_id) values (66,3);
insert into tbl_locations(id_, location_type_id) values (67,3);
insert into tbl_locations(id_, location_type_id) values (68,3);
insert into tbl_locations(id_, location_type_id) values (69,3);
insert into tbl_locations(id_, location_type_id) values (70,3);
insert into tbl_locations(id_, location_type_id) values (71,3);
insert into tbl_locations(id_, location_type_id) values (72,3);
insert into tbl_locations(id_, location_type_id) values (73,3);
insert into tbl_locations(id_, location_type_id) values (74,3);
insert into tbl_locations(id_, location_type_id) values (75,3);
insert into tbl_locations(id_, location_type_id) values (48029110100,4);
insert into tbl_locations(id_, location_type_id) values (48029110300,4);
insert into tbl_locations(id_, location_type_id) values (48029110500,4);
insert into tbl_locations(id_, location_type_id) values (48029110600,4);
insert into tbl_locations(id_, location_type_id) values (48029110700,4);
insert into tbl_locations(id_, location_type_id) values (48029111000,4);
insert into tbl_locations(id_, location_type_id) values (48029111100,4);
insert into tbl_locations(id_, location_type_id) values (48029120100,4);
insert into tbl_locations(id_, location_type_id) values (48029120301,4);
insert into tbl_locations(id_, location_type_id) values (48029120302,4);
insert into tbl_locations(id_, location_type_id) values (48029120401,4);
insert into tbl_locations(id_, location_type_id) values (48029120402,4);
insert into tbl_locations(id_, location_type_id) values (48029120502,4);
insert into tbl_locations(id_, location_type_id) values (48029120503,4);
insert into tbl_locations(id_, location_type_id) values (48029120504,4);
insert into tbl_locations(id_, location_type_id) values (48029120601,4);
insert into tbl_locations(id_, location_type_id) values (48029120602,4);
insert into tbl_locations(id_, location_type_id) values (48029120701,4);
insert into tbl_locations(id_, location_type_id) values (48029120702,4);
insert into tbl_locations(id_, location_type_id) values (48029120800,4);
insert into tbl_locations(id_, location_type_id) values (48029120901,4);
insert into tbl_locations(id_, location_type_id) values (48029120902,4);
insert into tbl_locations(id_, location_type_id) values (48029121000,4);
insert into tbl_locations(id_, location_type_id) values (48029121110,4);
insert into tbl_locations(id_, location_type_id) values (48029121111,4);
insert into tbl_locations(id_, location_type_id) values (48029121112,4);
insert into tbl_locations(id_, location_type_id) values (48029121115,4);
insert into tbl_locations(id_, location_type_id) values (48029121116,4);
insert into tbl_locations(id_, location_type_id) values (48029121117,4);
insert into tbl_locations(id_, location_type_id) values (48029121118,4);
insert into tbl_locations(id_, location_type_id) values (48029121119,4);
insert into tbl_locations(id_, location_type_id) values (48029121120,4);
insert into tbl_locations(id_, location_type_id) values (48029121121,4);
insert into tbl_locations(id_, location_type_id) values (48029121122,4);
insert into tbl_locations(id_, location_type_id) values (48029121123,4);
insert into tbl_locations(id_, location_type_id) values (48029121124,4);
insert into tbl_locations(id_, location_type_id) values (48029121203,4);
insert into tbl_locations(id_, location_type_id) values (48029121204,4);
insert into tbl_locations(id_, location_type_id) values (48029121205,4);
insert into tbl_locations(id_, location_type_id) values (48029121206,4);
insert into tbl_locations(id_, location_type_id) values (48029121300,4);
insert into tbl_locations(id_, location_type_id) values (48029121402,4);
insert into tbl_locations(id_, location_type_id) values (48029121403,4);
insert into tbl_locations(id_, location_type_id) values (48029121404,4);
insert into tbl_locations(id_, location_type_id) values (48029121501,4);
insert into tbl_locations(id_, location_type_id) values (48029121504,4);
insert into tbl_locations(id_, location_type_id) values (48029121505,4);
insert into tbl_locations(id_, location_type_id) values (48029121506,4);
insert into tbl_locations(id_, location_type_id) values (48029121507,4);
insert into tbl_locations(id_, location_type_id) values (48029121508,4);
insert into tbl_locations(id_, location_type_id) values (48029121601,4);
insert into tbl_locations(id_, location_type_id) values (48029121604,4);
insert into tbl_locations(id_, location_type_id) values (48029121605,4);
insert into tbl_locations(id_, location_type_id) values (48029121606,4);
insert into tbl_locations(id_, location_type_id) values (48029121701,4);
insert into tbl_locations(id_, location_type_id) values (48029121702,4);
insert into tbl_locations(id_, location_type_id) values (48029121802,4);
insert into tbl_locations(id_, location_type_id) values (48029121803,4);
insert into tbl_locations(id_, location_type_id) values (48029121804,4);
insert into tbl_locations(id_, location_type_id) values (48029121808,4);
insert into tbl_locations(id_, location_type_id) values (48029121809,4);
insert into tbl_locations(id_, location_type_id) values (48029121810,4);
insert into tbl_locations(id_, location_type_id) values (48029121811,4);
insert into tbl_locations(id_, location_type_id) values (48029121812,4);
insert into tbl_locations(id_, location_type_id) values (48029121813,4);
insert into tbl_locations(id_, location_type_id) values (48029121903,4);
insert into tbl_locations(id_, location_type_id) values (48029121904,4);
insert into tbl_locations(id_, location_type_id) values (48029121905,4);
insert into tbl_locations(id_, location_type_id) values (48029121906,4);
insert into tbl_locations(id_, location_type_id) values (48029121908,4);
insert into tbl_locations(id_, location_type_id) values (48029121909,4);
insert into tbl_locations(id_, location_type_id) values (48029121910,4);
insert into tbl_locations(id_, location_type_id) values (48029121911,4);
insert into tbl_locations(id_, location_type_id) values (48029121912,4);
insert into tbl_locations(id_, location_type_id) values (48029130200,4);
insert into tbl_locations(id_, location_type_id) values (48029130300,4);
insert into tbl_locations(id_, location_type_id) values (48029130401,4);
insert into tbl_locations(id_, location_type_id) values (48029130402,4);
insert into tbl_locations(id_, location_type_id) values (48029130500,4);
insert into tbl_locations(id_, location_type_id) values (48029130600,4);
insert into tbl_locations(id_, location_type_id) values (48029130700,4);
insert into tbl_locations(id_, location_type_id) values (48029130800,4);
insert into tbl_locations(id_, location_type_id) values (48029130900,4);
insert into tbl_locations(id_, location_type_id) values (48029131000,4);
insert into tbl_locations(id_, location_type_id) values (48029131100,4);
insert into tbl_locations(id_, location_type_id) values (48029131200,4);
insert into tbl_locations(id_, location_type_id) values (48029131300,4);
insert into tbl_locations(id_, location_type_id) values (48029131401,4);
insert into tbl_locations(id_, location_type_id) values (48029131402,4);
insert into tbl_locations(id_, location_type_id) values (48029131503,4);
insert into tbl_locations(id_, location_type_id) values (48029131504,4);
insert into tbl_locations(id_, location_type_id) values (48029131505,4);
insert into tbl_locations(id_, location_type_id) values (48029131506,4);
insert into tbl_locations(id_, location_type_id) values (48029131507,4);
insert into tbl_locations(id_, location_type_id) values (48029131601,4);
insert into tbl_locations(id_, location_type_id) values (48029131606,4);
insert into tbl_locations(id_, location_type_id) values (48029131608,4);
insert into tbl_locations(id_, location_type_id) values (48029131609,4);
insert into tbl_locations(id_, location_type_id) values (48029131610,4);
insert into tbl_locations(id_, location_type_id) values (48029131612,4);
insert into tbl_locations(id_, location_type_id) values (48029131614,4);
insert into tbl_locations(id_, location_type_id) values (48029131615,4);
insert into tbl_locations(id_, location_type_id) values (48029131616,4);
insert into tbl_locations(id_, location_type_id) values (48029131700,4);
insert into tbl_locations(id_, location_type_id) values (48029131801,4);
insert into tbl_locations(id_, location_type_id) values (48029131802,4);
insert into tbl_locations(id_, location_type_id) values (48029140100,4);
insert into tbl_locations(id_, location_type_id) values (48029140200,4);
insert into tbl_locations(id_, location_type_id) values (48029140300,4);
insert into tbl_locations(id_, location_type_id) values (48029140400,4);
insert into tbl_locations(id_, location_type_id) values (48029140500,4);
insert into tbl_locations(id_, location_type_id) values (48029140600,4);
insert into tbl_locations(id_, location_type_id) values (48029140700,4);
insert into tbl_locations(id_, location_type_id) values (48029140800,4);
insert into tbl_locations(id_, location_type_id) values (48029140900,4);
insert into tbl_locations(id_, location_type_id) values (48029141000,4);
insert into tbl_locations(id_, location_type_id) values (48029141101,4);
insert into tbl_locations(id_, location_type_id) values (48029141102,4);
insert into tbl_locations(id_, location_type_id) values (48029141200,4);
insert into tbl_locations(id_, location_type_id) values (48029141300,4);
insert into tbl_locations(id_, location_type_id) values (48029141402,4);
insert into tbl_locations(id_, location_type_id) values (48029141403,4);
insert into tbl_locations(id_, location_type_id) values (48029141404,4);
insert into tbl_locations(id_, location_type_id) values (48029141600,4);
insert into tbl_locations(id_, location_type_id) values (48029141700,4);
insert into tbl_locations(id_, location_type_id) values (48029141800,4);
insert into tbl_locations(id_, location_type_id) values (48029141900,4);
insert into tbl_locations(id_, location_type_id) values (48029150100,4);
insert into tbl_locations(id_, location_type_id) values (48029150300,4);
insert into tbl_locations(id_, location_type_id) values (48029150400,4);
insert into tbl_locations(id_, location_type_id) values (48029150501,4);
insert into tbl_locations(id_, location_type_id) values (48029150502,4);
insert into tbl_locations(id_, location_type_id) values (48029150600,4);
insert into tbl_locations(id_, location_type_id) values (48029150700,4);
insert into tbl_locations(id_, location_type_id) values (48029150800,4);
insert into tbl_locations(id_, location_type_id) values (48029150900,4);
insert into tbl_locations(id_, location_type_id) values (48029151000,4);
insert into tbl_locations(id_, location_type_id) values (48029151100,4);
insert into tbl_locations(id_, location_type_id) values (48029151200,4);
insert into tbl_locations(id_, location_type_id) values (48029151301,4);
insert into tbl_locations(id_, location_type_id) values (48029151302,4);
insert into tbl_locations(id_, location_type_id) values (48029151400,4);
insert into tbl_locations(id_, location_type_id) values (48029151500,4);
insert into tbl_locations(id_, location_type_id) values (48029151600,4);
insert into tbl_locations(id_, location_type_id) values (48029151700,4);
insert into tbl_locations(id_, location_type_id) values (48029151900,4);
insert into tbl_locations(id_, location_type_id) values (48029152000,4);
insert into tbl_locations(id_, location_type_id) values (48029152100,4);
insert into tbl_locations(id_, location_type_id) values (48029152201,4);
insert into tbl_locations(id_, location_type_id) values (48029152202,4);
insert into tbl_locations(id_, location_type_id) values (48029160100,4);
insert into tbl_locations(id_, location_type_id) values (48029160200,4);
insert into tbl_locations(id_, location_type_id) values (48029160300,4);
insert into tbl_locations(id_, location_type_id) values (48029160400,4);
insert into tbl_locations(id_, location_type_id) values (48029160501,4);
insert into tbl_locations(id_, location_type_id) values (48029160502,4);
insert into tbl_locations(id_, location_type_id) values (48029160600,4);
insert into tbl_locations(id_, location_type_id) values (48029160701,4);
insert into tbl_locations(id_, location_type_id) values (48029160702,4);
insert into tbl_locations(id_, location_type_id) values (48029160901,4);
insert into tbl_locations(id_, location_type_id) values (48029160902,4);
insert into tbl_locations(id_, location_type_id) values (48029161000,4);
insert into tbl_locations(id_, location_type_id) values (48029161100,4);
insert into tbl_locations(id_, location_type_id) values (48029161200,4);
insert into tbl_locations(id_, location_type_id) values (48029161302,4);
insert into tbl_locations(id_, location_type_id) values (48029161303,4);
insert into tbl_locations(id_, location_type_id) values (48029161304,4);
insert into tbl_locations(id_, location_type_id) values (48029161400,4);
insert into tbl_locations(id_, location_type_id) values (48029161501,4);
insert into tbl_locations(id_, location_type_id) values (48029161503,4);
insert into tbl_locations(id_, location_type_id) values (48029161504,4);
insert into tbl_locations(id_, location_type_id) values (48029161600,4);
insert into tbl_locations(id_, location_type_id) values (48029161801,4);
insert into tbl_locations(id_, location_type_id) values (48029161802,4);
insert into tbl_locations(id_, location_type_id) values (48029161901,4);
insert into tbl_locations(id_, location_type_id) values (48029161902,4);
insert into tbl_locations(id_, location_type_id) values (48029162001,4);
insert into tbl_locations(id_, location_type_id) values (48029162003,4);
insert into tbl_locations(id_, location_type_id) values (48029162004,4);
insert into tbl_locations(id_, location_type_id) values (48029170101,4);
insert into tbl_locations(id_, location_type_id) values (48029170102,4);
insert into tbl_locations(id_, location_type_id) values (48029170200,4);
insert into tbl_locations(id_, location_type_id) values (48029170300,4);
insert into tbl_locations(id_, location_type_id) values (48029170401,4);
insert into tbl_locations(id_, location_type_id) values (48029170402,4);
insert into tbl_locations(id_, location_type_id) values (48029170500,4);
insert into tbl_locations(id_, location_type_id) values (48029170600,4);
insert into tbl_locations(id_, location_type_id) values (48029170700,4);
insert into tbl_locations(id_, location_type_id) values (48029170800,4);
insert into tbl_locations(id_, location_type_id) values (48029170900,4);
insert into tbl_locations(id_, location_type_id) values (48029171000,4);
insert into tbl_locations(id_, location_type_id) values (48029171100,4);
insert into tbl_locations(id_, location_type_id) values (48029171200,4);
insert into tbl_locations(id_, location_type_id) values (48029171301,4);
insert into tbl_locations(id_, location_type_id) values (48029171302,4);
insert into tbl_locations(id_, location_type_id) values (48029171401,4);
insert into tbl_locations(id_, location_type_id) values (48029171402,4);
insert into tbl_locations(id_, location_type_id) values (48029171501,4);
insert into tbl_locations(id_, location_type_id) values (48029171502,4);
insert into tbl_locations(id_, location_type_id) values (48029171601,4);
insert into tbl_locations(id_, location_type_id) values (48029171602,4);
insert into tbl_locations(id_, location_type_id) values (48029171700,4);
insert into tbl_locations(id_, location_type_id) values (48029171801,4);
insert into tbl_locations(id_, location_type_id) values (48029171802,4);
insert into tbl_locations(id_, location_type_id) values (48029171903,4);
insert into tbl_locations(id_, location_type_id) values (48029171912,4);
insert into tbl_locations(id_, location_type_id) values (48029171913,4);
insert into tbl_locations(id_, location_type_id) values (48029171914,4);
insert into tbl_locations(id_, location_type_id) values (48029171915,4);
insert into tbl_locations(id_, location_type_id) values (48029171916,4);
insert into tbl_locations(id_, location_type_id) values (48029171917,4);
insert into tbl_locations(id_, location_type_id) values (48029171918,4);
insert into tbl_locations(id_, location_type_id) values (48029171919,4);
insert into tbl_locations(id_, location_type_id) values (48029171920,4);
insert into tbl_locations(id_, location_type_id) values (48029171921,4);
insert into tbl_locations(id_, location_type_id) values (48029171922,4);
insert into tbl_locations(id_, location_type_id) values (48029171923,4);
insert into tbl_locations(id_, location_type_id) values (48029171924,4);
insert into tbl_locations(id_, location_type_id) values (48029171926,4);
insert into tbl_locations(id_, location_type_id) values (48029171927,4);
insert into tbl_locations(id_, location_type_id) values (48029171928,4);
insert into tbl_locations(id_, location_type_id) values (48029171929,4);
insert into tbl_locations(id_, location_type_id) values (48029172002,4);
insert into tbl_locations(id_, location_type_id) values (48029172003,4);
insert into tbl_locations(id_, location_type_id) values (48029172004,4);
insert into tbl_locations(id_, location_type_id) values (48029172005,4);
insert into tbl_locations(id_, location_type_id) values (48029172006,4);
insert into tbl_locations(id_, location_type_id) values (48029172008,4);
insert into tbl_locations(id_, location_type_id) values (48029172009,4);
insert into tbl_locations(id_, location_type_id) values (48029180101,4);
insert into tbl_locations(id_, location_type_id) values (48029180102,4);
insert into tbl_locations(id_, location_type_id) values (48029180201,4);
insert into tbl_locations(id_, location_type_id) values (48029180202,4);
insert into tbl_locations(id_, location_type_id) values (48029180300,4);
insert into tbl_locations(id_, location_type_id) values (48029180400,4);
insert into tbl_locations(id_, location_type_id) values (48029180501,4);
insert into tbl_locations(id_, location_type_id) values (48029180503,4);
insert into tbl_locations(id_, location_type_id) values (48029180504,4);
insert into tbl_locations(id_, location_type_id) values (48029180602,4);
insert into tbl_locations(id_, location_type_id) values (48029180603,4);
insert into tbl_locations(id_, location_type_id) values (48029180604,4);
insert into tbl_locations(id_, location_type_id) values (48029180701,4);
insert into tbl_locations(id_, location_type_id) values (48029180702,4);
insert into tbl_locations(id_, location_type_id) values (48029180800,4);
insert into tbl_locations(id_, location_type_id) values (48029180901,4);
insert into tbl_locations(id_, location_type_id) values (48029180902,4);
insert into tbl_locations(id_, location_type_id) values (48029181001,4);
insert into tbl_locations(id_, location_type_id) values (48029181003,4);
insert into tbl_locations(id_, location_type_id) values (48029181004,4);
insert into tbl_locations(id_, location_type_id) values (48029181005,4);
insert into tbl_locations(id_, location_type_id) values (48029181100,4);
insert into tbl_locations(id_, location_type_id) values (48029181200,4);
insert into tbl_locations(id_, location_type_id) values (48029181301,4);
insert into tbl_locations(id_, location_type_id) values (48029181302,4);
insert into tbl_locations(id_, location_type_id) values (48029181303,4);
insert into tbl_locations(id_, location_type_id) values (48029181402,4);
insert into tbl_locations(id_, location_type_id) values (48029181403,4);
insert into tbl_locations(id_, location_type_id) values (48029181404,4);
insert into tbl_locations(id_, location_type_id) values (48029181503,4);
insert into tbl_locations(id_, location_type_id) values (48029181504,4);
insert into tbl_locations(id_, location_type_id) values (48029181505,4);
insert into tbl_locations(id_, location_type_id) values (48029181506,4);
insert into tbl_locations(id_, location_type_id) values (48029181601,4);
insert into tbl_locations(id_, location_type_id) values (48029181602,4);
insert into tbl_locations(id_, location_type_id) values (48029181703,4);
insert into tbl_locations(id_, location_type_id) values (48029181704,4);
insert into tbl_locations(id_, location_type_id) values (48029181705,4);
insert into tbl_locations(id_, location_type_id) values (48029181711,4);
insert into tbl_locations(id_, location_type_id) values (48029181712,4);
insert into tbl_locations(id_, location_type_id) values (48029181713,4);
insert into tbl_locations(id_, location_type_id) values (48029181715,4);
insert into tbl_locations(id_, location_type_id) values (48029181716,4);
insert into tbl_locations(id_, location_type_id) values (48029181718,4);
insert into tbl_locations(id_, location_type_id) values (48029181720,4);
insert into tbl_locations(id_, location_type_id) values (48029181721,4);
insert into tbl_locations(id_, location_type_id) values (48029181722,4);
insert into tbl_locations(id_, location_type_id) values (48029181723,4);
insert into tbl_locations(id_, location_type_id) values (48029181724,4);
insert into tbl_locations(id_, location_type_id) values (48029181725,4);
insert into tbl_locations(id_, location_type_id) values (48029181726,4);
insert into tbl_locations(id_, location_type_id) values (48029181727,4);
insert into tbl_locations(id_, location_type_id) values (48029181729,4);
insert into tbl_locations(id_, location_type_id) values (48029181730,4);
insert into tbl_locations(id_, location_type_id) values (48029181731,4);
insert into tbl_locations(id_, location_type_id) values (48029181732,4);
insert into tbl_locations(id_, location_type_id) values (48029181733,4);
insert into tbl_locations(id_, location_type_id) values (48029181808,4);
insert into tbl_locations(id_, location_type_id) values (48029181809,4);
insert into tbl_locations(id_, location_type_id) values (48029181811,4);
insert into tbl_locations(id_, location_type_id) values (48029181813,4);
insert into tbl_locations(id_, location_type_id) values (48029181814,4);
insert into tbl_locations(id_, location_type_id) values (48029181815,4);
insert into tbl_locations(id_, location_type_id) values (48029181816,4);
insert into tbl_locations(id_, location_type_id) values (48029181817,4);
insert into tbl_locations(id_, location_type_id) values (48029181818,4);
insert into tbl_locations(id_, location_type_id) values (48029181819,4);
insert into tbl_locations(id_, location_type_id) values (48029181820,4);
insert into tbl_locations(id_, location_type_id) values (48029181821,4);
insert into tbl_locations(id_, location_type_id) values (48029181822,4);
insert into tbl_locations(id_, location_type_id) values (48029181823,4);
insert into tbl_locations(id_, location_type_id) values (48029181824,4);
insert into tbl_locations(id_, location_type_id) values (48029181825,4);
insert into tbl_locations(id_, location_type_id) values (48029181826,4);
insert into tbl_locations(id_, location_type_id) values (48029181901,4);
insert into tbl_locations(id_, location_type_id) values (48029181902,4);
insert into tbl_locations(id_, location_type_id) values (48029182001,4);
insert into tbl_locations(id_, location_type_id) values (48029182002,4);
insert into tbl_locations(id_, location_type_id) values (48029182003,4);
insert into tbl_locations(id_, location_type_id) values (48029182101,4);
insert into tbl_locations(id_, location_type_id) values (48029182102,4);
insert into tbl_locations(id_, location_type_id) values (48029182103,4);
insert into tbl_locations(id_, location_type_id) values (48029182105,4);
insert into tbl_locations(id_, location_type_id) values (48029182106,4);
insert into tbl_locations(id_, location_type_id) values (48029190100,4);
insert into tbl_locations(id_, location_type_id) values (48029190200,4);
insert into tbl_locations(id_, location_type_id) values (48029190400,4);
insert into tbl_locations(id_, location_type_id) values (48029190501,4);
insert into tbl_locations(id_, location_type_id) values (48029190503,4);
insert into tbl_locations(id_, location_type_id) values (48029190504,4);
insert into tbl_locations(id_, location_type_id) values (48029190601,4);
insert into tbl_locations(id_, location_type_id) values (48029190603,4);
insert into tbl_locations(id_, location_type_id) values (48029190604,4);
insert into tbl_locations(id_, location_type_id) values (48029190700,4);
insert into tbl_locations(id_, location_type_id) values (48029190800,4);
insert into tbl_locations(id_, location_type_id) values (48029190901,4);
insert into tbl_locations(id_, location_type_id) values (48029190902,4);
insert into tbl_locations(id_, location_type_id) values (48029191003,4);
insert into tbl_locations(id_, location_type_id) values (48029191004,4);
insert into tbl_locations(id_, location_type_id) values (48029191005,4);
insert into tbl_locations(id_, location_type_id) values (48029191006,4);
insert into tbl_locations(id_, location_type_id) values (48029191101,4);
insert into tbl_locations(id_, location_type_id) values (48029191102,4);
insert into tbl_locations(id_, location_type_id) values (48029191201,4);
insert into tbl_locations(id_, location_type_id) values (48029191202,4);
insert into tbl_locations(id_, location_type_id) values (48029191303,4);
insert into tbl_locations(id_, location_type_id) values (48029191304,4);
insert into tbl_locations(id_, location_type_id) values (48029191405,4);
insert into tbl_locations(id_, location_type_id) values (48029191406,4);
insert into tbl_locations(id_, location_type_id) values (48029191408,4);
insert into tbl_locations(id_, location_type_id) values (48029191409,4);
insert into tbl_locations(id_, location_type_id) values (48029191410,4);
insert into tbl_locations(id_, location_type_id) values (48029191411,4);
insert into tbl_locations(id_, location_type_id) values (48029191412,4);
insert into tbl_locations(id_, location_type_id) values (48029191413,4);
insert into tbl_locations(id_, location_type_id) values (48029191503,4);
insert into tbl_locations(id_, location_type_id) values (48029191504,4);
insert into tbl_locations(id_, location_type_id) values (48029191505,4);
insert into tbl_locations(id_, location_type_id) values (48029191506,4);
insert into tbl_locations(id_, location_type_id) values (48029191701,4);
insert into tbl_locations(id_, location_type_id) values (48029191702,4);
insert into tbl_locations(id_, location_type_id) values (48029191804,4);
insert into tbl_locations(id_, location_type_id) values (48029191806,4);
insert into tbl_locations(id_, location_type_id) values (48029191807,4);
insert into tbl_locations(id_, location_type_id) values (48029191808,4);
insert into tbl_locations(id_, location_type_id) values (48029191809,4);
insert into tbl_locations(id_, location_type_id) values (48029191810,4);
insert into tbl_locations(id_, location_type_id) values (48029191811,4);
insert into tbl_locations(id_, location_type_id) values (48029191812,4);
insert into tbl_locations(id_, location_type_id) values (48029191813,4);
insert into tbl_locations(id_, location_type_id) values (48029191814,4);
insert into tbl_locations(id_, location_type_id) values (48029191815,4);
insert into tbl_locations(id_, location_type_id) values (48029191816,4);
insert into tbl_locations(id_, location_type_id) values (48029191818,4);
insert into tbl_locations(id_, location_type_id) values (48029191819,4);
insert into tbl_locations(id_, location_type_id) values (48029191900,4);
insert into tbl_locations(id_, location_type_id) values (48029192000,4);
insert into tbl_locations(id_, location_type_id) values (48029192100,4);
insert into tbl_locations(id_, location_type_id) values (48029192200,4);
insert into tbl_locations(id_, location_type_id) values (48029192300,4);
insert into tbl_locations(id_, location_type_id) values (48029980001,4);
insert into tbl_locations(id_, location_type_id) values (48029980002,4);
insert into tbl_locations(id_, location_type_id) values (48029980003,4);
insert into tbl_locations(id_, location_type_id) values (48029980004,4);
insert into tbl_locations(id_, location_type_id) values (48029980005,4);
insert into tbl_locations(id_, location_type_id) values (48029980100,4);
update tbl_locations set name_en = case 
	when location_type_id = 1 then 'Council District ' || id_
	when location_type_id = 2 then 'Zip Code ' || id_
	when location_type_id = 3 then 'SSA ' || id_
	when location_type_id = 4 then 'Census Tract ' || 
		case when substring(id_::text,10,2) = '00' then substring(id_::text,6,4)
			else substring(id_::text,6,4) || '.' || substring(id_::text,10,2) end
end;
update tbl_locations set name_es = case 
	when location_type_id = 1 then 'Council District (es) ' || id_
	when location_type_id = 2 then 'Zip Code (es) ' || id_
	when location_type_id = 3 then 'SSA (es)' || id_
	when location_type_id = 4 then 'Census Tract (es) ' || 
		case when substring(id_::text,10,2) = '00' then substring(id_::text,6,4)
			else substring(id_::text,6,4) || '.' || substring(id_::text,10,2) end
end;
alter table tbl_locations alter column name_en set not null;
alter table tbl_locations alter column name_es set not null;

insert into tbl_filter_types(id_, name_en, name_es)
values (1, 'Race or Ethnicity', 'Race or Ethnicity (es)'),
(2, 'Age', 'Age (es)'),
(3, 'Sex', 'Sex (es)'),
(4, 'Education Level', 'Education Level (es)'),
(5, 'Income Level', 'Income Level (es)');

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order)
values (1, 1, 'All', 'All (es)', 1),
(2, 1, 'AIAN', 'AIAN (es)', 2),
(3, 1, 'Asian', 'Asian (es)', 3),
(4, 2, 'All', 'All (es)', 1),
(5, 2, 'Under 18', 'Under 18 (es)', 2),
(6, 2, '65 and Over', '65 and Over (es)', 3),
(7, 3, 'All', 'All (es)', 1),
(8, 3, 'Female', 'Female (es)', 2),
(9, 3, 'Male', 'Male (es)', 3),
(10, 4, 'High School Diploma', 'High School Diploma (es)', 1),
(11, 4, 'Two-year Degree', 'Two-year Degree (es)', 2),
(12, 5, 'Below Poverty Level', 'Below Poverty Level (es)', 1),
(13, 5, 'At or Above Poverty Level', 'At or Above Poverty Level (es)', 2);

insert into tbl_sources (id_, name_en, name_es, url_)
values (1, 'ACS 5-year Estimates', 'ACS 5-year Estimates (es)', 'https://data.census.gov');

insert into tbl_indicator_categories (id_, name_en, name_es, sort_order)
values (1, 'Demographics', 'Demographics 1 (es)', 1), 
	(2, 'Education', 'Education (es)', 3),
	(3, 'Economics', 'Economics (es)', 2);

insert into tbl_indicator_types (id_, name_)
values (1, 'Count'), (2, 'Percent'), (3, 'Currency');

insert into tbl_indicators (id_, indicator_category_id, indicator_type_id, name_en, name_es, description_en, description_es, base_filter_type_id)
values (1, 1, 1, 'Population', 'Population (es)', 'Population is...', 'Population is...(es)', null),
	(2, 3, 3, 'Median Household Income', 'Median Household Income (es)', 'Median Household Income...', 'Indicator 2 is...(es)', null),
	(3, 2, 2, 'Educational Attainment', 'Educational Attainment (es)', 'Educational Attainment is...', 'Educational Attainment is...(es)', 4);

--changeset herronrb:3
insert into tbl_indicator_values (indicator_id,year_,source_id,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020',1,1,1,487,4,4,487,1,null,null,null,null),
	(1,'2020',1,1,1,18,12,12,487,2,null,null,null,null),
	(1,'2020',1,1,1,71,9,9,487,3,null,null,null,null),
	(2,'2020',1,1,1,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020',1,1,1,51430,1300,700,null,2,null,null,null,null),
	(2,'2020',1,1,1,53430,1300,700,null,3,null,null,null,null),
	(3,'2020',1,1,1,28.5,2,6,487,1,null,null,10,null),
	(3,'2020',1,1,1,31.4,3,7,487,1,null,null,11,null),
	(3,'2020',1,1,1,17.6,2,6,43,2,null,null,10,null),
	(3,'2020',1,1,1,22.8,3,7,43,2,null,null,11,null);

--changeset herronrb:4
insert into tbl_indicator_values (indicator_id,year_,source_id,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016',1,1,1,482,4,4,482,1,null,null,null,null),
	(1,'2016',1,1,1,16,12,12,482,2,null,null,null,null),
	(1,'2016',1,1,1,70,9,9,482,3,null,null,null,null),
	(3,'2016',1,1,1,25.1,2,6,482,1,null,null,10,null),
	(3,'2016',1,1,1,18.3,3,7,482,2,null,null,11,null);

--changeset herronrb:5
update tbl_location_types set sort_order = 6 where sort_order = 5;
update tbl_location_types set sort_order = 5 where sort_order = 4;
update tbl_location_types set sort_order = 4 where sort_order = 3;
update tbl_location_types set sort_order = 3 where sort_order = 2;
update tbl_location_types set sort_order = 2 where sort_order = 1;
insert into tbl_location_types (id_, name_en, name_es, sort_order)
values (5, 'Counties', 'Counties (es)', 1);
insert into tbl_locations(id_, location_type_id, name_en, name_es) 
values (48029,5, 'Bexar County', 'Bexar County (es)');

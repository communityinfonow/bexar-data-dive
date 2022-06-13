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
	base_filter_type_id numeric references tbl_filter_types,
	source_id numeric references tbl_sources
);

create table tbl_indicator_values (
	indicator_id numeric not null references tbl_indicators,
	year_ text not null,
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
create unique index idx_indicator_values on tbl_indicator_values (indicator_id, year_, location_id, location_type_id, race_id, age_id, sex_id, education_id, income_id);

--changeset herronrb:2
insert into tbl_location_types (id_, name_en, name_es, sort_order)
values (1, 'Counties', 'Counties (es)', 1),
	(2, 'Council Districts', 'Council Districts (es)', 2), 
	(3, 'Zip Codes', 'Zip Codes (es)', 3), 
	(4, 'SSA''s', 'SSA''s (es)', 4),
	(5, 'Census Tracts', 'Census Tracts (es)', 5);

insert into tbl_locations(id_, location_type_id, name_en, name_es) 
values (48029, 1, 'Bexar County', 'Bexar County (es)');
insert into tbl_locations(id_, location_type_id) values (1,2);
insert into tbl_locations(id_, location_type_id) values (2,2);
insert into tbl_locations(id_, location_type_id) values (3,2);
insert into tbl_locations(id_, location_type_id) values (4,2);
insert into tbl_locations(id_, location_type_id) values (5,2);
insert into tbl_locations(id_, location_type_id) values (6,2);
insert into tbl_locations(id_, location_type_id) values (7,2);
insert into tbl_locations(id_, location_type_id) values (8,2);
insert into tbl_locations(id_, location_type_id) values (9,2);
insert into tbl_locations(id_, location_type_id) values (10,2);
insert into tbl_locations(id_, location_type_id) values (78002,3);
insert into tbl_locations(id_, location_type_id) values (78015,3);
insert into tbl_locations(id_, location_type_id) values (78023,3);
insert into tbl_locations(id_, location_type_id) values (78069,3);
insert into tbl_locations(id_, location_type_id) values (78073,3);
insert into tbl_locations(id_, location_type_id) values (78101,3);
insert into tbl_locations(id_, location_type_id) values (78109,3);
insert into tbl_locations(id_, location_type_id) values (78112,3);
insert into tbl_locations(id_, location_type_id) values (78148,3);
insert into tbl_locations(id_, location_type_id) values (78150,3);
insert into tbl_locations(id_, location_type_id) values (78152,3);
insert into tbl_locations(id_, location_type_id) values (78154,3);
insert into tbl_locations(id_, location_type_id) values (78201,3);
insert into tbl_locations(id_, location_type_id) values (78202,3);
insert into tbl_locations(id_, location_type_id) values (78203,3);
insert into tbl_locations(id_, location_type_id) values (78204,3);
insert into tbl_locations(id_, location_type_id) values (78205,3);
insert into tbl_locations(id_, location_type_id) values (78207,3);
insert into tbl_locations(id_, location_type_id) values (78208,3);
insert into tbl_locations(id_, location_type_id) values (78209,3);
insert into tbl_locations(id_, location_type_id) values (78210,3);
insert into tbl_locations(id_, location_type_id) values (78211,3);
insert into tbl_locations(id_, location_type_id) values (78212,3);
insert into tbl_locations(id_, location_type_id) values (78213,3);
insert into tbl_locations(id_, location_type_id) values (78214,3);
insert into tbl_locations(id_, location_type_id) values (78215,3);
insert into tbl_locations(id_, location_type_id) values (78216,3);
insert into tbl_locations(id_, location_type_id) values (78217,3);
insert into tbl_locations(id_, location_type_id) values (78218,3);
insert into tbl_locations(id_, location_type_id) values (78219,3);
insert into tbl_locations(id_, location_type_id) values (78220,3);
insert into tbl_locations(id_, location_type_id) values (78221,3);
insert into tbl_locations(id_, location_type_id) values (78222,3);
insert into tbl_locations(id_, location_type_id) values (78223,3);
insert into tbl_locations(id_, location_type_id) values (78224,3);
insert into tbl_locations(id_, location_type_id) values (78225,3);
insert into tbl_locations(id_, location_type_id) values (78226,3);
insert into tbl_locations(id_, location_type_id) values (78227,3);
insert into tbl_locations(id_, location_type_id) values (78228,3);
insert into tbl_locations(id_, location_type_id) values (78229,3);
insert into tbl_locations(id_, location_type_id) values (78230,3);
insert into tbl_locations(id_, location_type_id) values (78231,3);
insert into tbl_locations(id_, location_type_id) values (78232,3);
insert into tbl_locations(id_, location_type_id) values (78233,3);
insert into tbl_locations(id_, location_type_id) values (78234,3);
insert into tbl_locations(id_, location_type_id) values (78235,3);
insert into tbl_locations(id_, location_type_id) values (78236,3);
insert into tbl_locations(id_, location_type_id) values (78237,3);
insert into tbl_locations(id_, location_type_id) values (78238,3);
insert into tbl_locations(id_, location_type_id) values (78239,3);
insert into tbl_locations(id_, location_type_id) values (78240,3);
insert into tbl_locations(id_, location_type_id) values (78242,3);
insert into tbl_locations(id_, location_type_id) values (78243,3);
insert into tbl_locations(id_, location_type_id) values (78244,3);
insert into tbl_locations(id_, location_type_id) values (78245,3);
insert into tbl_locations(id_, location_type_id) values (78247,3);
insert into tbl_locations(id_, location_type_id) values (78248,3);
insert into tbl_locations(id_, location_type_id) values (78249,3);
insert into tbl_locations(id_, location_type_id) values (78250,3);
insert into tbl_locations(id_, location_type_id) values (78251,3);
insert into tbl_locations(id_, location_type_id) values (78252,3);
insert into tbl_locations(id_, location_type_id) values (78253,3);
insert into tbl_locations(id_, location_type_id) values (78254,3);
insert into tbl_locations(id_, location_type_id) values (78255,3);
insert into tbl_locations(id_, location_type_id) values (78256,3);
insert into tbl_locations(id_, location_type_id) values (78257,3);
insert into tbl_locations(id_, location_type_id) values (78258,3);
insert into tbl_locations(id_, location_type_id) values (78259,3);
insert into tbl_locations(id_, location_type_id) values (78260,3);
insert into tbl_locations(id_, location_type_id) values (78261,3);
insert into tbl_locations(id_, location_type_id) values (78263,3);
insert into tbl_locations(id_, location_type_id) values (78264,3);
insert into tbl_locations(id_, location_type_id) values (78266,3);
insert into tbl_locations(id_, location_type_id) values (1,4);
insert into tbl_locations(id_, location_type_id) values (2,4);
insert into tbl_locations(id_, location_type_id) values (3,4);
insert into tbl_locations(id_, location_type_id) values (4,4);
insert into tbl_locations(id_, location_type_id) values (5,4);
insert into tbl_locations(id_, location_type_id) values (6,4);
insert into tbl_locations(id_, location_type_id) values (7,4);
insert into tbl_locations(id_, location_type_id) values (8,4);
insert into tbl_locations(id_, location_type_id) values (9,4);
insert into tbl_locations(id_, location_type_id) values (10,4);
insert into tbl_locations(id_, location_type_id) values (11,4);
insert into tbl_locations(id_, location_type_id) values (12,4);
insert into tbl_locations(id_, location_type_id) values (13,4);
insert into tbl_locations(id_, location_type_id) values (14,4);
insert into tbl_locations(id_, location_type_id) values (15,4);
insert into tbl_locations(id_, location_type_id) values (16,4);
insert into tbl_locations(id_, location_type_id) values (17,4);
insert into tbl_locations(id_, location_type_id) values (18,4);
insert into tbl_locations(id_, location_type_id) values (19,4);
insert into tbl_locations(id_, location_type_id) values (20,4);
insert into tbl_locations(id_, location_type_id) values (21,4);
insert into tbl_locations(id_, location_type_id) values (22,4);
insert into tbl_locations(id_, location_type_id) values (23,4);
insert into tbl_locations(id_, location_type_id) values (24,4);
insert into tbl_locations(id_, location_type_id) values (25,4);
insert into tbl_locations(id_, location_type_id) values (26,4);
insert into tbl_locations(id_, location_type_id) values (27,4);
insert into tbl_locations(id_, location_type_id) values (28,4);
insert into tbl_locations(id_, location_type_id) values (29,4);
insert into tbl_locations(id_, location_type_id) values (30,4);
insert into tbl_locations(id_, location_type_id) values (31,4);
insert into tbl_locations(id_, location_type_id) values (32,4);
insert into tbl_locations(id_, location_type_id) values (33,4);
insert into tbl_locations(id_, location_type_id) values (34,4);
insert into tbl_locations(id_, location_type_id) values (35,4);
insert into tbl_locations(id_, location_type_id) values (36,4);
insert into tbl_locations(id_, location_type_id) values (37,4);
insert into tbl_locations(id_, location_type_id) values (38,4);
insert into tbl_locations(id_, location_type_id) values (39,4);
insert into tbl_locations(id_, location_type_id) values (40,4);
insert into tbl_locations(id_, location_type_id) values (41,4);
insert into tbl_locations(id_, location_type_id) values (42,4);
insert into tbl_locations(id_, location_type_id) values (43,4);
insert into tbl_locations(id_, location_type_id) values (44,4);
insert into tbl_locations(id_, location_type_id) values (45,4);
insert into tbl_locations(id_, location_type_id) values (46,4);
insert into tbl_locations(id_, location_type_id) values (47,4);
insert into tbl_locations(id_, location_type_id) values (48,4);
insert into tbl_locations(id_, location_type_id) values (49,4);
insert into tbl_locations(id_, location_type_id) values (50,4);
insert into tbl_locations(id_, location_type_id) values (51,4);
insert into tbl_locations(id_, location_type_id) values (52,4);
insert into tbl_locations(id_, location_type_id) values (53,4);
insert into tbl_locations(id_, location_type_id) values (54,4);
insert into tbl_locations(id_, location_type_id) values (55,4);
insert into tbl_locations(id_, location_type_id) values (56,4);
insert into tbl_locations(id_, location_type_id) values (57,4);
insert into tbl_locations(id_, location_type_id) values (58,4);
insert into tbl_locations(id_, location_type_id) values (59,4);
insert into tbl_locations(id_, location_type_id) values (60,4);
insert into tbl_locations(id_, location_type_id) values (61,4);
insert into tbl_locations(id_, location_type_id) values (62,4);
insert into tbl_locations(id_, location_type_id) values (63,4);
insert into tbl_locations(id_, location_type_id) values (64,4);
insert into tbl_locations(id_, location_type_id) values (65,4);
insert into tbl_locations(id_, location_type_id) values (66,4);
insert into tbl_locations(id_, location_type_id) values (67,4);
insert into tbl_locations(id_, location_type_id) values (68,4);
insert into tbl_locations(id_, location_type_id) values (69,4);
insert into tbl_locations(id_, location_type_id) values (70,4);
insert into tbl_locations(id_, location_type_id) values (71,4);
insert into tbl_locations(id_, location_type_id) values (72,4);
insert into tbl_locations(id_, location_type_id) values (73,4);
insert into tbl_locations(id_, location_type_id) values (74,4);
insert into tbl_locations(id_, location_type_id) values (75,4);
insert into tbl_locations(id_, location_type_id) values (48029110100,5);
insert into tbl_locations(id_, location_type_id) values (48029110300,5);
insert into tbl_locations(id_, location_type_id) values (48029110500,5);
insert into tbl_locations(id_, location_type_id) values (48029110600,5);
insert into tbl_locations(id_, location_type_id) values (48029110700,5);
insert into tbl_locations(id_, location_type_id) values (48029111000,5);
insert into tbl_locations(id_, location_type_id) values (48029111100,5);
insert into tbl_locations(id_, location_type_id) values (48029120100,5);
insert into tbl_locations(id_, location_type_id) values (48029120301,5);
insert into tbl_locations(id_, location_type_id) values (48029120302,5);
insert into tbl_locations(id_, location_type_id) values (48029120401,5);
insert into tbl_locations(id_, location_type_id) values (48029120402,5);
insert into tbl_locations(id_, location_type_id) values (48029120502,5);
insert into tbl_locations(id_, location_type_id) values (48029120503,5);
insert into tbl_locations(id_, location_type_id) values (48029120504,5);
insert into tbl_locations(id_, location_type_id) values (48029120601,5);
insert into tbl_locations(id_, location_type_id) values (48029120602,5);
insert into tbl_locations(id_, location_type_id) values (48029120701,5);
insert into tbl_locations(id_, location_type_id) values (48029120702,5);
insert into tbl_locations(id_, location_type_id) values (48029120800,5);
insert into tbl_locations(id_, location_type_id) values (48029120901,5);
insert into tbl_locations(id_, location_type_id) values (48029120902,5);
insert into tbl_locations(id_, location_type_id) values (48029121000,5);
insert into tbl_locations(id_, location_type_id) values (48029121110,5);
insert into tbl_locations(id_, location_type_id) values (48029121111,5);
insert into tbl_locations(id_, location_type_id) values (48029121112,5);
insert into tbl_locations(id_, location_type_id) values (48029121115,5);
insert into tbl_locations(id_, location_type_id) values (48029121116,5);
insert into tbl_locations(id_, location_type_id) values (48029121117,5);
insert into tbl_locations(id_, location_type_id) values (48029121118,5);
insert into tbl_locations(id_, location_type_id) values (48029121119,5);
insert into tbl_locations(id_, location_type_id) values (48029121120,5);
insert into tbl_locations(id_, location_type_id) values (48029121121,5);
insert into tbl_locations(id_, location_type_id) values (48029121122,5);
insert into tbl_locations(id_, location_type_id) values (48029121123,5);
insert into tbl_locations(id_, location_type_id) values (48029121124,5);
insert into tbl_locations(id_, location_type_id) values (48029121203,5);
insert into tbl_locations(id_, location_type_id) values (48029121204,5);
insert into tbl_locations(id_, location_type_id) values (48029121205,5);
insert into tbl_locations(id_, location_type_id) values (48029121206,5);
insert into tbl_locations(id_, location_type_id) values (48029121300,5);
insert into tbl_locations(id_, location_type_id) values (48029121402,5);
insert into tbl_locations(id_, location_type_id) values (48029121403,5);
insert into tbl_locations(id_, location_type_id) values (48029121404,5);
insert into tbl_locations(id_, location_type_id) values (48029121501,5);
insert into tbl_locations(id_, location_type_id) values (48029121504,5);
insert into tbl_locations(id_, location_type_id) values (48029121505,5);
insert into tbl_locations(id_, location_type_id) values (48029121506,5);
insert into tbl_locations(id_, location_type_id) values (48029121507,5);
insert into tbl_locations(id_, location_type_id) values (48029121508,5);
insert into tbl_locations(id_, location_type_id) values (48029121601,5);
insert into tbl_locations(id_, location_type_id) values (48029121604,5);
insert into tbl_locations(id_, location_type_id) values (48029121605,5);
insert into tbl_locations(id_, location_type_id) values (48029121606,5);
insert into tbl_locations(id_, location_type_id) values (48029121701,5);
insert into tbl_locations(id_, location_type_id) values (48029121702,5);
insert into tbl_locations(id_, location_type_id) values (48029121802,5);
insert into tbl_locations(id_, location_type_id) values (48029121803,5);
insert into tbl_locations(id_, location_type_id) values (48029121804,5);
insert into tbl_locations(id_, location_type_id) values (48029121808,5);
insert into tbl_locations(id_, location_type_id) values (48029121809,5);
insert into tbl_locations(id_, location_type_id) values (48029121810,5);
insert into tbl_locations(id_, location_type_id) values (48029121811,5);
insert into tbl_locations(id_, location_type_id) values (48029121812,5);
insert into tbl_locations(id_, location_type_id) values (48029121813,5);
insert into tbl_locations(id_, location_type_id) values (48029121903,5);
insert into tbl_locations(id_, location_type_id) values (48029121904,5);
insert into tbl_locations(id_, location_type_id) values (48029121905,5);
insert into tbl_locations(id_, location_type_id) values (48029121906,5);
insert into tbl_locations(id_, location_type_id) values (48029121908,5);
insert into tbl_locations(id_, location_type_id) values (48029121909,5);
insert into tbl_locations(id_, location_type_id) values (48029121910,5);
insert into tbl_locations(id_, location_type_id) values (48029121911,5);
insert into tbl_locations(id_, location_type_id) values (48029121912,5);
insert into tbl_locations(id_, location_type_id) values (48029130200,5);
insert into tbl_locations(id_, location_type_id) values (48029130300,5);
insert into tbl_locations(id_, location_type_id) values (48029130401,5);
insert into tbl_locations(id_, location_type_id) values (48029130402,5);
insert into tbl_locations(id_, location_type_id) values (48029130500,5);
insert into tbl_locations(id_, location_type_id) values (48029130600,5);
insert into tbl_locations(id_, location_type_id) values (48029130700,5);
insert into tbl_locations(id_, location_type_id) values (48029130800,5);
insert into tbl_locations(id_, location_type_id) values (48029130900,5);
insert into tbl_locations(id_, location_type_id) values (48029131000,5);
insert into tbl_locations(id_, location_type_id) values (48029131100,5);
insert into tbl_locations(id_, location_type_id) values (48029131200,5);
insert into tbl_locations(id_, location_type_id) values (48029131300,5);
insert into tbl_locations(id_, location_type_id) values (48029131401,5);
insert into tbl_locations(id_, location_type_id) values (48029131402,5);
insert into tbl_locations(id_, location_type_id) values (48029131503,5);
insert into tbl_locations(id_, location_type_id) values (48029131504,5);
insert into tbl_locations(id_, location_type_id) values (48029131505,5);
insert into tbl_locations(id_, location_type_id) values (48029131506,5);
insert into tbl_locations(id_, location_type_id) values (48029131507,5);
insert into tbl_locations(id_, location_type_id) values (48029131601,5);
insert into tbl_locations(id_, location_type_id) values (48029131606,5);
insert into tbl_locations(id_, location_type_id) values (48029131608,5);
insert into tbl_locations(id_, location_type_id) values (48029131609,5);
insert into tbl_locations(id_, location_type_id) values (48029131610,5);
insert into tbl_locations(id_, location_type_id) values (48029131612,5);
insert into tbl_locations(id_, location_type_id) values (48029131614,5);
insert into tbl_locations(id_, location_type_id) values (48029131615,5);
insert into tbl_locations(id_, location_type_id) values (48029131616,5);
insert into tbl_locations(id_, location_type_id) values (48029131700,5);
insert into tbl_locations(id_, location_type_id) values (48029131801,5);
insert into tbl_locations(id_, location_type_id) values (48029131802,5);
insert into tbl_locations(id_, location_type_id) values (48029140100,5);
insert into tbl_locations(id_, location_type_id) values (48029140200,5);
insert into tbl_locations(id_, location_type_id) values (48029140300,5);
insert into tbl_locations(id_, location_type_id) values (48029140400,5);
insert into tbl_locations(id_, location_type_id) values (48029140500,5);
insert into tbl_locations(id_, location_type_id) values (48029140600,5);
insert into tbl_locations(id_, location_type_id) values (48029140700,5);
insert into tbl_locations(id_, location_type_id) values (48029140800,5);
insert into tbl_locations(id_, location_type_id) values (48029140900,5);
insert into tbl_locations(id_, location_type_id) values (48029141000,5);
insert into tbl_locations(id_, location_type_id) values (48029141101,5);
insert into tbl_locations(id_, location_type_id) values (48029141102,5);
insert into tbl_locations(id_, location_type_id) values (48029141200,5);
insert into tbl_locations(id_, location_type_id) values (48029141300,5);
insert into tbl_locations(id_, location_type_id) values (48029141402,5);
insert into tbl_locations(id_, location_type_id) values (48029141403,5);
insert into tbl_locations(id_, location_type_id) values (48029141404,5);
insert into tbl_locations(id_, location_type_id) values (48029141600,5);
insert into tbl_locations(id_, location_type_id) values (48029141700,5);
insert into tbl_locations(id_, location_type_id) values (48029141800,5);
insert into tbl_locations(id_, location_type_id) values (48029141900,5);
insert into tbl_locations(id_, location_type_id) values (48029150100,5);
insert into tbl_locations(id_, location_type_id) values (48029150300,5);
insert into tbl_locations(id_, location_type_id) values (48029150400,5);
insert into tbl_locations(id_, location_type_id) values (48029150501,5);
insert into tbl_locations(id_, location_type_id) values (48029150502,5);
insert into tbl_locations(id_, location_type_id) values (48029150600,5);
insert into tbl_locations(id_, location_type_id) values (48029150700,5);
insert into tbl_locations(id_, location_type_id) values (48029150800,5);
insert into tbl_locations(id_, location_type_id) values (48029150900,5);
insert into tbl_locations(id_, location_type_id) values (48029151000,5);
insert into tbl_locations(id_, location_type_id) values (48029151100,5);
insert into tbl_locations(id_, location_type_id) values (48029151200,5);
insert into tbl_locations(id_, location_type_id) values (48029151301,5);
insert into tbl_locations(id_, location_type_id) values (48029151302,5);
insert into tbl_locations(id_, location_type_id) values (48029151400,5);
insert into tbl_locations(id_, location_type_id) values (48029151500,5);
insert into tbl_locations(id_, location_type_id) values (48029151600,5);
insert into tbl_locations(id_, location_type_id) values (48029151700,5);
insert into tbl_locations(id_, location_type_id) values (48029151900,5);
insert into tbl_locations(id_, location_type_id) values (48029152000,5);
insert into tbl_locations(id_, location_type_id) values (48029152100,5);
insert into tbl_locations(id_, location_type_id) values (48029152201,5);
insert into tbl_locations(id_, location_type_id) values (48029152202,5);
insert into tbl_locations(id_, location_type_id) values (48029160100,5);
insert into tbl_locations(id_, location_type_id) values (48029160200,5);
insert into tbl_locations(id_, location_type_id) values (48029160300,5);
insert into tbl_locations(id_, location_type_id) values (48029160400,5);
insert into tbl_locations(id_, location_type_id) values (48029160501,5);
insert into tbl_locations(id_, location_type_id) values (48029160502,5);
insert into tbl_locations(id_, location_type_id) values (48029160600,5);
insert into tbl_locations(id_, location_type_id) values (48029160701,5);
insert into tbl_locations(id_, location_type_id) values (48029160702,5);
insert into tbl_locations(id_, location_type_id) values (48029160901,5);
insert into tbl_locations(id_, location_type_id) values (48029160902,5);
insert into tbl_locations(id_, location_type_id) values (48029161000,5);
insert into tbl_locations(id_, location_type_id) values (48029161100,5);
insert into tbl_locations(id_, location_type_id) values (48029161200,5);
insert into tbl_locations(id_, location_type_id) values (48029161302,5);
insert into tbl_locations(id_, location_type_id) values (48029161303,5);
insert into tbl_locations(id_, location_type_id) values (48029161304,5);
insert into tbl_locations(id_, location_type_id) values (48029161400,5);
insert into tbl_locations(id_, location_type_id) values (48029161501,5);
insert into tbl_locations(id_, location_type_id) values (48029161503,5);
insert into tbl_locations(id_, location_type_id) values (48029161504,5);
insert into tbl_locations(id_, location_type_id) values (48029161600,5);
insert into tbl_locations(id_, location_type_id) values (48029161801,5);
insert into tbl_locations(id_, location_type_id) values (48029161802,5);
insert into tbl_locations(id_, location_type_id) values (48029161901,5);
insert into tbl_locations(id_, location_type_id) values (48029161902,5);
insert into tbl_locations(id_, location_type_id) values (48029162001,5);
insert into tbl_locations(id_, location_type_id) values (48029162003,5);
insert into tbl_locations(id_, location_type_id) values (48029162004,5);
insert into tbl_locations(id_, location_type_id) values (48029170101,5);
insert into tbl_locations(id_, location_type_id) values (48029170102,5);
insert into tbl_locations(id_, location_type_id) values (48029170200,5);
insert into tbl_locations(id_, location_type_id) values (48029170300,5);
insert into tbl_locations(id_, location_type_id) values (48029170401,5);
insert into tbl_locations(id_, location_type_id) values (48029170402,5);
insert into tbl_locations(id_, location_type_id) values (48029170500,5);
insert into tbl_locations(id_, location_type_id) values (48029170600,5);
insert into tbl_locations(id_, location_type_id) values (48029170700,5);
insert into tbl_locations(id_, location_type_id) values (48029170800,5);
insert into tbl_locations(id_, location_type_id) values (48029170900,5);
insert into tbl_locations(id_, location_type_id) values (48029171000,5);
insert into tbl_locations(id_, location_type_id) values (48029171100,5);
insert into tbl_locations(id_, location_type_id) values (48029171200,5);
insert into tbl_locations(id_, location_type_id) values (48029171301,5);
insert into tbl_locations(id_, location_type_id) values (48029171302,5);
insert into tbl_locations(id_, location_type_id) values (48029171401,5);
insert into tbl_locations(id_, location_type_id) values (48029171402,5);
insert into tbl_locations(id_, location_type_id) values (48029171501,5);
insert into tbl_locations(id_, location_type_id) values (48029171502,5);
insert into tbl_locations(id_, location_type_id) values (48029171601,5);
insert into tbl_locations(id_, location_type_id) values (48029171602,5);
insert into tbl_locations(id_, location_type_id) values (48029171700,5);
insert into tbl_locations(id_, location_type_id) values (48029171801,5);
insert into tbl_locations(id_, location_type_id) values (48029171802,5);
insert into tbl_locations(id_, location_type_id) values (48029171903,5);
insert into tbl_locations(id_, location_type_id) values (48029171912,5);
insert into tbl_locations(id_, location_type_id) values (48029171913,5);
insert into tbl_locations(id_, location_type_id) values (48029171914,5);
insert into tbl_locations(id_, location_type_id) values (48029171915,5);
insert into tbl_locations(id_, location_type_id) values (48029171916,5);
insert into tbl_locations(id_, location_type_id) values (48029171917,5);
insert into tbl_locations(id_, location_type_id) values (48029171918,5);
insert into tbl_locations(id_, location_type_id) values (48029171919,5);
insert into tbl_locations(id_, location_type_id) values (48029171920,5);
insert into tbl_locations(id_, location_type_id) values (48029171921,5);
insert into tbl_locations(id_, location_type_id) values (48029171922,5);
insert into tbl_locations(id_, location_type_id) values (48029171923,5);
insert into tbl_locations(id_, location_type_id) values (48029171924,5);
insert into tbl_locations(id_, location_type_id) values (48029171926,5);
insert into tbl_locations(id_, location_type_id) values (48029171927,5);
insert into tbl_locations(id_, location_type_id) values (48029171928,5);
insert into tbl_locations(id_, location_type_id) values (48029171929,5);
insert into tbl_locations(id_, location_type_id) values (48029172002,5);
insert into tbl_locations(id_, location_type_id) values (48029172003,5);
insert into tbl_locations(id_, location_type_id) values (48029172004,5);
insert into tbl_locations(id_, location_type_id) values (48029172005,5);
insert into tbl_locations(id_, location_type_id) values (48029172006,5);
insert into tbl_locations(id_, location_type_id) values (48029172008,5);
insert into tbl_locations(id_, location_type_id) values (48029172009,5);
insert into tbl_locations(id_, location_type_id) values (48029180101,5);
insert into tbl_locations(id_, location_type_id) values (48029180102,5);
insert into tbl_locations(id_, location_type_id) values (48029180201,5);
insert into tbl_locations(id_, location_type_id) values (48029180202,5);
insert into tbl_locations(id_, location_type_id) values (48029180300,5);
insert into tbl_locations(id_, location_type_id) values (48029180400,5);
insert into tbl_locations(id_, location_type_id) values (48029180501,5);
insert into tbl_locations(id_, location_type_id) values (48029180503,5);
insert into tbl_locations(id_, location_type_id) values (48029180504,5);
insert into tbl_locations(id_, location_type_id) values (48029180602,5);
insert into tbl_locations(id_, location_type_id) values (48029180603,5);
insert into tbl_locations(id_, location_type_id) values (48029180604,5);
insert into tbl_locations(id_, location_type_id) values (48029180701,5);
insert into tbl_locations(id_, location_type_id) values (48029180702,5);
insert into tbl_locations(id_, location_type_id) values (48029180800,5);
insert into tbl_locations(id_, location_type_id) values (48029180901,5);
insert into tbl_locations(id_, location_type_id) values (48029180902,5);
insert into tbl_locations(id_, location_type_id) values (48029181001,5);
insert into tbl_locations(id_, location_type_id) values (48029181003,5);
insert into tbl_locations(id_, location_type_id) values (48029181004,5);
insert into tbl_locations(id_, location_type_id) values (48029181005,5);
insert into tbl_locations(id_, location_type_id) values (48029181100,5);
insert into tbl_locations(id_, location_type_id) values (48029181200,5);
insert into tbl_locations(id_, location_type_id) values (48029181301,5);
insert into tbl_locations(id_, location_type_id) values (48029181302,5);
insert into tbl_locations(id_, location_type_id) values (48029181303,5);
insert into tbl_locations(id_, location_type_id) values (48029181402,5);
insert into tbl_locations(id_, location_type_id) values (48029181403,5);
insert into tbl_locations(id_, location_type_id) values (48029181404,5);
insert into tbl_locations(id_, location_type_id) values (48029181503,5);
insert into tbl_locations(id_, location_type_id) values (48029181504,5);
insert into tbl_locations(id_, location_type_id) values (48029181505,5);
insert into tbl_locations(id_, location_type_id) values (48029181506,5);
insert into tbl_locations(id_, location_type_id) values (48029181601,5);
insert into tbl_locations(id_, location_type_id) values (48029181602,5);
insert into tbl_locations(id_, location_type_id) values (48029181703,5);
insert into tbl_locations(id_, location_type_id) values (48029181704,5);
insert into tbl_locations(id_, location_type_id) values (48029181705,5);
insert into tbl_locations(id_, location_type_id) values (48029181711,5);
insert into tbl_locations(id_, location_type_id) values (48029181712,5);
insert into tbl_locations(id_, location_type_id) values (48029181713,5);
insert into tbl_locations(id_, location_type_id) values (48029181715,5);
insert into tbl_locations(id_, location_type_id) values (48029181716,5);
insert into tbl_locations(id_, location_type_id) values (48029181718,5);
insert into tbl_locations(id_, location_type_id) values (48029181720,5);
insert into tbl_locations(id_, location_type_id) values (48029181721,5);
insert into tbl_locations(id_, location_type_id) values (48029181722,5);
insert into tbl_locations(id_, location_type_id) values (48029181723,5);
insert into tbl_locations(id_, location_type_id) values (48029181724,5);
insert into tbl_locations(id_, location_type_id) values (48029181725,5);
insert into tbl_locations(id_, location_type_id) values (48029181726,5);
insert into tbl_locations(id_, location_type_id) values (48029181727,5);
insert into tbl_locations(id_, location_type_id) values (48029181729,5);
insert into tbl_locations(id_, location_type_id) values (48029181730,5);
insert into tbl_locations(id_, location_type_id) values (48029181731,5);
insert into tbl_locations(id_, location_type_id) values (48029181732,5);
insert into tbl_locations(id_, location_type_id) values (48029181733,5);
insert into tbl_locations(id_, location_type_id) values (48029181808,5);
insert into tbl_locations(id_, location_type_id) values (48029181809,5);
insert into tbl_locations(id_, location_type_id) values (48029181811,5);
insert into tbl_locations(id_, location_type_id) values (48029181813,5);
insert into tbl_locations(id_, location_type_id) values (48029181814,5);
insert into tbl_locations(id_, location_type_id) values (48029181815,5);
insert into tbl_locations(id_, location_type_id) values (48029181816,5);
insert into tbl_locations(id_, location_type_id) values (48029181817,5);
insert into tbl_locations(id_, location_type_id) values (48029181818,5);
insert into tbl_locations(id_, location_type_id) values (48029181819,5);
insert into tbl_locations(id_, location_type_id) values (48029181820,5);
insert into tbl_locations(id_, location_type_id) values (48029181821,5);
insert into tbl_locations(id_, location_type_id) values (48029181822,5);
insert into tbl_locations(id_, location_type_id) values (48029181823,5);
insert into tbl_locations(id_, location_type_id) values (48029181824,5);
insert into tbl_locations(id_, location_type_id) values (48029181825,5);
insert into tbl_locations(id_, location_type_id) values (48029181826,5);
insert into tbl_locations(id_, location_type_id) values (48029181901,5);
insert into tbl_locations(id_, location_type_id) values (48029181902,5);
insert into tbl_locations(id_, location_type_id) values (48029182001,5);
insert into tbl_locations(id_, location_type_id) values (48029182002,5);
insert into tbl_locations(id_, location_type_id) values (48029182003,5);
insert into tbl_locations(id_, location_type_id) values (48029182101,5);
insert into tbl_locations(id_, location_type_id) values (48029182102,5);
insert into tbl_locations(id_, location_type_id) values (48029182103,5);
insert into tbl_locations(id_, location_type_id) values (48029182105,5);
insert into tbl_locations(id_, location_type_id) values (48029182106,5);
insert into tbl_locations(id_, location_type_id) values (48029190100,5);
insert into tbl_locations(id_, location_type_id) values (48029190200,5);
insert into tbl_locations(id_, location_type_id) values (48029190400,5);
insert into tbl_locations(id_, location_type_id) values (48029190501,5);
insert into tbl_locations(id_, location_type_id) values (48029190503,5);
insert into tbl_locations(id_, location_type_id) values (48029190504,5);
insert into tbl_locations(id_, location_type_id) values (48029190601,5);
insert into tbl_locations(id_, location_type_id) values (48029190603,5);
insert into tbl_locations(id_, location_type_id) values (48029190604,5);
insert into tbl_locations(id_, location_type_id) values (48029190700,5);
insert into tbl_locations(id_, location_type_id) values (48029190800,5);
insert into tbl_locations(id_, location_type_id) values (48029190901,5);
insert into tbl_locations(id_, location_type_id) values (48029190902,5);
insert into tbl_locations(id_, location_type_id) values (48029191003,5);
insert into tbl_locations(id_, location_type_id) values (48029191004,5);
insert into tbl_locations(id_, location_type_id) values (48029191005,5);
insert into tbl_locations(id_, location_type_id) values (48029191006,5);
insert into tbl_locations(id_, location_type_id) values (48029191101,5);
insert into tbl_locations(id_, location_type_id) values (48029191102,5);
insert into tbl_locations(id_, location_type_id) values (48029191201,5);
insert into tbl_locations(id_, location_type_id) values (48029191202,5);
insert into tbl_locations(id_, location_type_id) values (48029191303,5);
insert into tbl_locations(id_, location_type_id) values (48029191304,5);
insert into tbl_locations(id_, location_type_id) values (48029191405,5);
insert into tbl_locations(id_, location_type_id) values (48029191406,5);
insert into tbl_locations(id_, location_type_id) values (48029191408,5);
insert into tbl_locations(id_, location_type_id) values (48029191409,5);
insert into tbl_locations(id_, location_type_id) values (48029191410,5);
insert into tbl_locations(id_, location_type_id) values (48029191411,5);
insert into tbl_locations(id_, location_type_id) values (48029191412,5);
insert into tbl_locations(id_, location_type_id) values (48029191413,5);
insert into tbl_locations(id_, location_type_id) values (48029191503,5);
insert into tbl_locations(id_, location_type_id) values (48029191504,5);
insert into tbl_locations(id_, location_type_id) values (48029191505,5);
insert into tbl_locations(id_, location_type_id) values (48029191506,5);
insert into tbl_locations(id_, location_type_id) values (48029191701,5);
insert into tbl_locations(id_, location_type_id) values (48029191702,5);
insert into tbl_locations(id_, location_type_id) values (48029191804,5);
insert into tbl_locations(id_, location_type_id) values (48029191806,5);
insert into tbl_locations(id_, location_type_id) values (48029191807,5);
insert into tbl_locations(id_, location_type_id) values (48029191808,5);
insert into tbl_locations(id_, location_type_id) values (48029191809,5);
insert into tbl_locations(id_, location_type_id) values (48029191810,5);
insert into tbl_locations(id_, location_type_id) values (48029191811,5);
insert into tbl_locations(id_, location_type_id) values (48029191812,5);
insert into tbl_locations(id_, location_type_id) values (48029191813,5);
insert into tbl_locations(id_, location_type_id) values (48029191814,5);
insert into tbl_locations(id_, location_type_id) values (48029191815,5);
insert into tbl_locations(id_, location_type_id) values (48029191816,5);
insert into tbl_locations(id_, location_type_id) values (48029191818,5);
insert into tbl_locations(id_, location_type_id) values (48029191819,5);
insert into tbl_locations(id_, location_type_id) values (48029191900,5);
insert into tbl_locations(id_, location_type_id) values (48029192000,5);
insert into tbl_locations(id_, location_type_id) values (48029192100,5);
insert into tbl_locations(id_, location_type_id) values (48029192200,5);
insert into tbl_locations(id_, location_type_id) values (48029192300,5);
insert into tbl_locations(id_, location_type_id) values (48029980001,5);
insert into tbl_locations(id_, location_type_id) values (48029980002,5);
insert into tbl_locations(id_, location_type_id) values (48029980003,5);
insert into tbl_locations(id_, location_type_id) values (48029980004,5);
insert into tbl_locations(id_, location_type_id) values (48029980005,5);
insert into tbl_locations(id_, location_type_id) values (48029980100,5);
update tbl_locations set name_en = case 
	when location_type_id = 2 then 'Council District ' || id_
	when location_type_id = 3 then 'Zip Code ' || id_
	when location_type_id = 4 then 'SSA ' || id_
	when location_type_id = 5 then 'Census Tract ' || 
		case when substring(id_::text,10,2) = '00' then substring(id_::text,6,4)
			else substring(id_::text,6,4) || '.' || substring(id_::text,10,2) end
	else name_en 
end;
update tbl_locations set name_es = case 
	when location_type_id = 2 then 'Council District (es) ' || id_
	when location_type_id = 3 then 'Zip Code (es) ' || id_
	when location_type_id = 4 then 'SSA (es)' || id_
	when location_type_id = 5 then 'Census Tract (es) ' || 
		case when substring(id_::text,10,2) = '00' then substring(id_::text,6,4)
			else substring(id_::text,6,4) || '.' || substring(id_::text,10,2) end
	else name_es 
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
values (1, 'ACS 5-year', ' (es)', null),
	(2, 'BCAD', 'BCAD (es)', null),
	(3, 'Bexar County Elections', 'Bexar County Elections (es)', null),
	(4, 'Bexar County Juvenile Probation Dept.', 'Bexar County Juvenile Probation Dept. (es)', null),
	(5, 'CDC', 'CDC (es)', null),
	(6, 'COSA', 'COSA (es)', null),
	(7, 'COSA NHSD', 'COSA NHSD (es)', null),
	(8, 'Decennial Census', 'Decennial Census (es)', null),
	(9, 'EDI', 'EDI (es)', null),
	(10, 'EPA NATA', 'EPA NATA (es)', null),
	(11, 'FBI UCR', 'FBI UCR (es)', null),
	(12, 'HASA', 'HASA (es)', null),
	(13, 'HHS', 'HHS (es)', null),
	(14, 'HUD', 'HUD (es)', null),
	(15, 'LEHD', 'LEHD (es)', null),
	(16, 'National Vital Statistics Report', 'National Vital Statistics Report (es)', null),
	(17, 'Natl Housing Preservation DB', 'Natl Housing Preservation DB (es)', null),
	(18, 'PLACES', 'PLACES (es)', null),
	(19, 'San Antonio Metro Health', 'San Antonio Metro Health (es)', null),
	(20, 'SAPD', 'SAPD (es)', null),
	(21, 'TDFPS', 'TDFPS (es)', null),
	(22, 'TDPS', 'TDPS (es)', null),
	(23, 'TDSHS', 'TDSHS (es)', null),
	(24, 'THCIC Hospital Discharge PUDF', 'THCIC Hospital Discharge PUDF (es)', null),
	(25, 'United Way of San Antonio and Bexar County', 'United Way of San Antonio and Bexar County (es)', null),
	(26, 'US Census', 'US Census (es)', null),
	(27, 'USDA Food Atlas', 'USDA Food Atlas (es)', null);

insert into tbl_indicator_categories (id_, name_en, name_es, sort_order)
values (1, 'Civic & social', 'Civic & social (es)', 1),
(2, 'Criminal justice', 'Criminal justice (es)', 2),
(3, 'Demographic', 'Demographic (es)', 3),
(4, 'Economic', 'Economic (es)', 4),
(5, 'Education', 'Education (es)', 5),
(6, 'Environment', 'Environment (es)', 6),
(7, 'Health behaviors', 'Health behaviors (es)', 7),
(8, 'Health outcomes', 'Health outcomes', 8),
(9, 'Housing', 'Housing (es)', 9),
(10, 'Transportation', 'Transportation (es)', 10);

insert into tbl_indicator_types (id_, name_)
values (1, 'Count'), (2, 'Percent'), (3, 'Currency');

insert into tbl_indicators (id_, indicator_category_id, indicator_type_id, source_id, name_en, name_es, description_en, description_es)
values (1, 1, 1, 26, '2020 Census response rate', '2020 Census response rate (es)', '[description]...', '[description]...(es)'),
	(2, 1, 1, 18, 'Adequate social-emotional support', 'Adequate social-emotional support (es)', '[description]...', '[description]...(es)'),
	(3, 1, 1, 3, 'Voter registration', 'Voter registration (es)', '[description]...', '[description]...(es)'),
	(4, 1, 1, 3, 'Voter turnout', 'Voter turnout (es)', '[description]...', '[description]...(es)'),
	(5, 2, 1, 2, 'Absentee property owners with two or more property maintenance violations within a 12-month period', 'Absentee property owners with two or more property maintenance violations within a 12-month period (es)', '[description]...', '[description]...(es)'),
	(6, 2, 1, 22, 'Aggravated assaults per 100K', 'Aggravated assaults per 100K (es)', '[description]...', '[description]...(es)'),
	(7, 2, 1, 22, 'Burglaries per 100K', 'Burglaries per 100K (es)', '[description]...', '[description]...(es)'),
	(8, 2, 1, 21, 'Child abuse prevalence (intakes)', 'Child abuse prevalence (intakes) (es)', '[description]...', '[description]...(es)'),
	(9, 2, 1, 21, 'Child abuse prevalence (confirmed victims)', 'Child abuse prevalence (confirmed victims) (es)', '[description]...', '[description]...(es)'),
	(10, 2, 1, 21, 'Child abuse prevalence (revictimized)', 'Child abuse prevalence (revictimized) (es)', '[description]...', '[description]...(es)'),
	(11, 2, 1, 20, 'Drug-related arrest rate', 'Drug-related arrest rate (es)', '[description]...', '[description]...(es)'),
	(12, 2, 1, 22, 'Family violence rate', 'Family violence rate (es)', '[description]...', '[description]...(es)'),
	(13, 2, 1, 22, 'Homicide and manslaughter rate', 'Homicide and manslaughter rate (es)', '[description]...', '[description]...(es)'),
	(14, 2, 1, 4, 'Juvenile probation rate', 'Juvenile probation rate (es)', '[description]...', '[description]...(es)'),
	(15, 2, 1, 8, 'Incarcerated as of 2020 Census', 'Incarcerated as of 2020 Census (es)', '[description]...', '[description]...(es)'),
	(16, 2, 1, 20, 'Property crime rate', 'Property crime rate (es)', '[description]...', '[description]...(es)'),
	(17, 2, 1, 11, 'Reported hate crime offense rate', 'Reported hate crime offense rate (es)', '[description]...', '[description]...(es)'),
	(18, 2, 1, 20, 'Sexual assault rate', 'Sexual assault rate (es)', '[description]...', '[description]...(es)'),
	(19, 2, 1, 20, 'Arrest rate', 'Arrest rate (es)', '[description]...', '[description]...(es)'),
	(20, 2, 1, 20, 'Violent crime rate', 'Violent crime rate (es)', '[description]...', '[description]...(es)'),
	(21, 3, 1, 1, 'Age Group', 'Age Group (es)', '[description]...', '[description]...(es)'),
	(22, 3, 1, 1, 'Limited English proficiency', 'Limited English proficiency (es)', '[description]...', '[description]...(es)'),
	(23, 3, 1, 1, 'Increase in population in past 10 years', 'Increase in population in past 10 years (es)', '[description]...', '[description]...(es)'),
	(24, 3, 1, 1, 'Households that are multigenerational', 'Households that are multigenerational (es)', '[description]...', '[description]...(es)'),
	(25, 3, 1, 1, 'Race', 'Race (es)', '[description]...', '[description]...(es)'),
	(26, 3, 1, 1, 'Racial/ethnic diversity (Theil index)', 'Racial/ethnic diversity (Theil index) (es)', '[description]...', '[description]...(es)'),
	(27, 3, 1, 1, 'Sex', 'Sex (es)', '[description]...', '[description]...(es)'),
	(28, 3, 1, 1, 'Speak Spanish or Spanish Creole at home', 'Speak Spanish or Spanish Creole at home (es)', '[description]...', '[description]...(es)'),
	(29, 3, 1, 1, 'Veteran population', 'Veteran population (es)', '[description]...', '[description]...(es)'),
	(30, 4, 1, 14, 'Access to loans and capital', 'Access to loans and capital (es)', '[description]...', '[description]...(es)'),
	(31, 4, 1, 25, 'ALICE households', 'ALICE households (es)', '[description]...', '[description]...(es)'),
	(32, 4, 1, 14, 'Assistance (food, rent assistance)', 'Assistance (food, rent assistance) (es)', '[description]...', '[description]...(es)'),
	(33, 4, 1, 13, 'Child care capacity per 1K kids (measure tbd)', 'Child care capacity per 1K kids (measure tbd) (es)', '[description]...', '[description]...(es)'),
	(34, 4, 1, 1, 'Computer and broadband Subscription', 'Computer and broadband Subscription (es)', '[description]...', '[description]...(es)'),
	(35, 4, 1, 1, 'Employment status', 'Employment status (es)', '[description]...', '[description]...(es)'),
	(36, 4, 1, 1, 'Home ownership', 'Home ownership (es)', '[description]...', '[description]...(es)'),
	(37, 4, 1, 1, 'Housing cost burden', 'Housing cost burden (es)', '[description]...', '[description]...(es)'),
	(38, 4, 1, 1, 'Income inequality', 'Income inequality (es)', '[description]...', '[description]...(es)'),
	(39, 4, 1, 15, 'Jobs per population', 'Jobs per population (es)', '[description]...', '[description]...(es)'),
	(40, 4, 1, 1, 'Family Income (median)', 'Family Income (median) (es)', '[description]...', '[description]...(es)'),
	(41, 4, 1, 1, 'Household Income (median)', 'Household Income (median) (es)', '[description]...', '[description]...(es)'),
	(42, 4, 1, 15, 'Monthly Earnings', 'Monthly Earnings (es)', '[description]...', '[description]...(es)'),
	(43, 4, 1, 1, 'Households with income <30% AMI', 'Households with income <30% AMI (es)', '[description]...', '[description]...(es)'),
	(44, 4, 1, 1, 'Households with income <60% AMI', 'Households with income <60% AMI (es)', '[description]...', '[description]...(es)'),
	(45, 4, 1, 1, 'Households with income <80% AMI', 'Households with income <80% AMI (es)', '[description]...', '[description]...(es)'),
	(46, 4, 1, 1, 'Percent not in labor force and not employed', 'Percent not in labor force and not employed (es)', '[description]...', '[description]...(es)'),
	(47, 4, 1, 1, 'Population in Armed Forces', 'Population in Armed Forces (es)', '[description]...', '[description]...(es)'),
	(48, 4, 1, 1, 'Income below poverty', 'Income below poverty (es)', '[description]...', '[description]...(es)'),
	(49, 4, 1, 7, 'Rental assistance', 'Rental assistance (es)', '[description]...', '[description]...(es)'),
	(50, 4, 1, 1, 'Supplemental Security Income (SSI)', 'Supplemental Security Income (SSI) (es)', '[description]...', '[description]...(es)'),
	(51, 4, 1, 1, 'Unemployment', 'Unemployment (es)', '[description]...', '[description]...(es)'),
	(52, 4, 1, 1, 'Uninsured', 'Uninsured (es)', '[description]...', '[description]...(es)'),
	(53, 4, 1, 1, 'Youth not in school or not working', 'Youth not in school or not working (es)', '[description]...', '[description]...(es)'),
	(54, 5, 1, 1, 'Education less than high school diploma/GED', 'Education less than high school diploma/GED (es)', '[description]...', '[description]...(es)'),
	(55, 5, 1, 1, 'Education Bachelor''s degree or higher', 'Education Bachelor''s degree or higher (es)', '[description]...', '[description]...(es)'),
	(56, 5, 1, 1, 'Enrolled in preschool', 'Enrolled in preschool (es)', '[description]...', '[description]...(es)'),
	(57, 5, 1, 13, 'Children in high-quality pre-K or daycare', 'Children in high-quality pre-K or daycare (es)', '[description]...', '[description]...(es)'),
	(58, 5, 1, 9, 'Children scoring "Vulnerable" on kinder-readiness', 'Children scoring "Vulnerable" on kinder-readiness (es)', '[description]...', '[description]...(es)'),
	(59, 6, 1, 6, 'Access to parks and greenspace', 'Access to parks and greenspace (es)', '[description]...', '[description]...(es)'),
	(60, 6, 1, 10, 'Air toxins cancer risk index', 'Air toxins cancer risk index (es)', '[description]...', '[description]...(es)'),
	(61, 6, 1, 14, 'Environmental health hazard index', 'Environmental health hazard index (es)', '[description]...', '[description]...(es)'),
	(62, 6, 1, 27, 'Food insecurity/food deserts', 'Food insecurity/food deserts (es)', '[description]...', '[description]...(es)'),
	(63, 7, 1, 18, 'Sleeping less than seven hours among adults', 'Sleeping less than seven hours among adults (es)', '[description]...', '[description]...(es)'),
	(64, 7, 1, 18, 'Cholesterol screening among adults', 'Cholesterol screening among adults (es)', '[description]...', '[description]...(es)'),
	(65, 7, 1, 19, 'COVID-19 vaccination rates', 'COVID-19 vaccination rates (es)', '[description]...', '[description]...(es)'),
	(66, 7, 1, 18, 'Obesity among adults', 'Obesity among adults (es)', '[description]...', '[description]...(es)'),
	(67, 7, 1, 18, 'Older adults aged 65 years and older who are up to date on a core set of clinical preventive services', 'Older adults aged 65 years and older who are up to date on a core set of clinical preventive services (es)', '[description]...', '[description]...(es)'),
	(68, 7, 1, 23, 'Opioid prescription rate', 'Opioid prescription rate (es)', '[description]...', '[description]...(es)'),
	(69, 7, 1, 18, 'Cervical cancer Pap screening among adult women aged 21-65 years', 'Cervical cancer Pap screening among adult women aged 21-65 years (es)', '[description]...', '[description]...(es)'),
	(70, 7, 1, 18, 'Current smoking among adults', 'Current smoking among adults (es)', '[description]...', '[description]...(es)'),
	(71, 7, 1, 19, 'Prenatal care', 'Prenatal care (es)', '[description]...', '[description]...(es)'),
	(72, 7, 1, 18, 'Visits to dentist or dental clinic among adults', 'Visits to dentist or dental clinic among adults (es)', '[description]...', '[description]...(es)'),
	(73, 7, 1, 18, 'Visits to doctor for routine checkup within the past year among adults', 'Visits to doctor for routine checkup within the past year among adults (es)', '[description]...', '[description]...(es)'),
	(74, 8, 1, 12, 'Asthma hospitalization rate', 'Asthma hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(75, 8, 1, 19, 'Birth rates', 'Birth rates (es)', '[description]...', '[description]...(es)'),
	(76, 8, 1, 18, 'Coronary heart disease among adults', 'Coronary heart disease among adults (es)', '[description]...', '[description]...(es)'),
	(77, 8, 1, 19, 'COVID-19 case rate', 'COVID-19 case rate (es)', '[description]...', '[description]...(es)'),
	(78, 8, 1, 19, 'COVID-19 death rate', 'COVID-19 death rate (es)', '[description]...', '[description]...(es)'),
	(79, 8, 1, 23, 'Death rate by age', 'Death rate by age (es)', '[description]...', '[description]...(es)'),
	(80, 8, 1, 12, 'Diabetes hospitalization rate', 'Diabetes hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(81, 8, 1, 18, 'Diabetes among adults', 'Diabetes among adults (es)', '[description]...', '[description]...(es)'),
	(82, 8, 1, 1, 'Disability status', 'Disability status (es)', '[description]...', '[description]...(es)'),
	(83, 8, 1, 24, 'Hypertension hospitalization rate', 'Hypertension hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(84, 8, 1, 24, 'Injury hospitalization rate', 'Injury hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(85, 8, 1, 5, 'Invasive cancer incidence rate', 'Invasive cancer incidence rate (es)', '[description]...', '[description]...(es)'),
	(86, 8, 1, 23, 'Lead poisoning/elevated blood lead', 'Lead poisoning/elevated blood lead (es)', '[description]...', '[description]...(es)'),
	(87, 8, 1, 16, 'Life expectancy (average)', 'Life expectancy (average) (es)', '[description]...', '[description]...(es)'),
	(88, 8, 1, 24, 'Mental health hospitalization rate', 'Mental health hospitalization rate (es)', '[description]...', '[description]...(es)'),
	(89, 8, 1, 18, 'Mental health not good for >=14 days among adults', 'Mental health not good for >=14 days among adults (es)', '[description]...', '[description]...(es)'),
	(90, 8, 1, 18, 'Physical health not good for >=14 days among adults', 'Physical health not good for >=14 days among adults (es)', '[description]...', '[description]...(es)'),
	(91, 8, 1, 18, 'Stroke among adults', 'Stroke among adults (es)', '[description]...', '[description]...(es)'),
	(92, 9, 1, 1, 'Change in total housing units (10-year change)', 'Change in total housing units (10-year change) (es)', '[description]...', '[description]...(es)'),
	(93, 9, 1, 1, 'Multi-family housing units', 'Multi-family housing units (es)', '[description]...', '[description]...(es)'),
	(94, 9, 1, 17, 'Housing units subsidized', 'Housing units subsidized (es)', '[description]...', '[description]...(es)'),
	(95, 9, 1, 2, 'Residential buildings in fair or poor condition', 'Residential buildings in fair or poor condition (es)', '[description]...', '[description]...(es)'),
	(96, 9, 1, 1, 'Renter housing stability (moved in past year)', 'Renter housing stability (moved in past year) (es)', '[description]...', '[description]...(es)'),
	(97, 9, 1, 1, 'Vacant housing units', 'Vacant housing units (es)', '[description]...', '[description]...(es)'),
	(98, 10, 1, 14, 'Low Transportation Cost Index', 'Low Transportation Cost Index (es)', '[description]...', '[description]...(es)');

--changeset herronrb:3
insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020',48029,1,487,4,4,487,1,null,null,null,null),
	(1,'2020',48029,1,18,12,12,487,2,null,null,null,null),
	(1,'2020',48029,1,71,9,9,487,3,null,null,null,null),
	(2,'2020',48029,1,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020',48029,1,51430,1300,700,null,2,null,null,null,null),
	(2,'2020',48029,1,53430,1300,700,null,3,null,null,null,null),
	(3,'2020',48029,1,28.5,2,6,487,1,null,null,10,null),
	(3,'2020',48029,1,31.4,3,7,487,1,null,null,11,null),
	(3,'2020',48029,1,17.6,2,6,43,2,null,null,10,null),
	(3,'2020',48029,1,22.8,3,7,43,2,null,null,11,null);

--changeset herronrb:4
insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016',48029,1,482,4,4,482,1,null,null,null,null),
	(1,'2016',48029,1,16,12,12,482,2,null,null,null,null),
	(1,'2016',48029,1,70,9,9,482,3,null,null,null,null),
	(3,'2016',48029,1,25.1,2,6,482,1,null,null,10,null),
	(3,'2016',48029,1,18.3,3,7,482,2,null,null,11,null);

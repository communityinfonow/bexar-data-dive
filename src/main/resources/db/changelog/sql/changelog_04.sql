--liquibase formatted sql

--changeset herronrb:indicators3

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order)
values 
	('314',	2, '5 to 17 years', '5 to 17 years (es)', 15),
	('315',	2, '18 to 34 years', '18 to 34 years (es)', 16),
	('316',	2, '35 to 64 years', '35 to 64 years (es)', 17),
	('317',	2, '25 to 34 years', '25 to 34 years (es)', 18),
	('319',	2, '45 to 64 years', '45 to 64 years (es)', 19),
	('320',	2, '65 years and over', '65 years and over (es)', 20),
	('321',	2, 'Under 18 years', 'Under 18 years (es)', 21),
	('322',	2, '18 to 64 years', '18 to 64 years (es)', 22)
;
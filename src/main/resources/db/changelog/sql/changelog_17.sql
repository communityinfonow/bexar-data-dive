--liquibase formatted sql

--changeset herronrb:source_and_age_updates
update tbl_sources set name_en = 'CDC PLACES, BRFSS (County level)', name_es = 'CDC PLACES, BRFSS (County level) (es)' where id_ = 18;

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order) values
(318, 2, '25 to 34 years', '25 to 34 years (es)', 98),
(325, 2, '15 to 24 years', '15 to 24 years (es)', 99);

update tbl_filter_options set name_en = '18 to 24 years', name_es = '18 to 24 years (es)' where id_ = 317;

update tbl_filter_options set sort_order = 5 where id_ = 300;
update tbl_filter_options set sort_order = 6 where id_ = 301;
update tbl_filter_options set sort_order = 11 where id_ = 302;
update tbl_filter_options set sort_order = 15 where id_ = 303;
update tbl_filter_options set sort_order = 17 where id_ = 304;
update tbl_filter_options set sort_order = 22 where id_ = 305;
update tbl_filter_options set sort_order = 26 where id_ = 306;
update tbl_filter_options set sort_order = 31 where id_ = 307;
update tbl_filter_options set sort_order = 36 where id_ = 308;
update tbl_filter_options set sort_order = 46 where id_ = 309;
update tbl_filter_options set sort_order = 56 where id_ = 310;
update tbl_filter_options set sort_order = 66 where id_ = 311;
update tbl_filter_options set sort_order = 76 where id_ = 312;
update tbl_filter_options set sort_order = 85 where id_ = 313;
update tbl_filter_options set sort_order = 8 where id_ = 314;
update tbl_filter_options set sort_order = 20 where id_ = 315;
update tbl_filter_options set sort_order = 37 where id_ = 316;
update tbl_filter_options set sort_order = 19 where id_ = 317;
update tbl_filter_options set sort_order = 27 where id_ = 318;
update tbl_filter_options set sort_order = 47 where id_ = 319;
update tbl_filter_options set sort_order = 65 where id_ = 320;
update tbl_filter_options set sort_order = 18 where id_ = 321;
update tbl_filter_options set sort_order = 21 where id_ = 322;
update tbl_filter_options set sort_order = 25 where id_ = 323;
update tbl_filter_options set sort_order = 28 where id_ = 324;
update tbl_filter_options set sort_order = 16 where id_ = 325;

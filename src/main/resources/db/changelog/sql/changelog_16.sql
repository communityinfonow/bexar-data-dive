--liquibase formatted sql

--changeset herronrb:age_updates
update tbl_filter_options
set sort_order = 40 where type_id = 2 and id_ = 320;

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order) values
(323, 2, 'Under 25 years', 'Under 25 years (es)', 23),
(324, 2, '25 to 44 years', '25 to 44 years (es)', 24);
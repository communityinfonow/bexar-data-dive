--liquibase formatted sql

--changeset herronrb:new_income_filter
insert into tbl_filter_options(id_, type_id, name_en, name_es, sort_order) values (503, 5, 'At or above poverty level', 'At or above poverty level (es)', 4) on conflict do nothing;
--liquibase formatted sql

--changeset herronrb:aggregation_prep_2

alter table tbl_indicator_values add universe_moe numeric;
alter table tbl_indicator_values add count_moe numeric;
alter table tbl_indicator_values__deleted add universe_moe numeric;
alter table tbl_indicator_values__deleted add count_moe numeric;
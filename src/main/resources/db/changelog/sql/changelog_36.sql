--liquibase formatted sql

--changeset herronrb:aggregation_prep_1

alter table tbl_indicator_values add count_value numeric;
alter table tbl_indicator_values__deleted add count_value numeric;
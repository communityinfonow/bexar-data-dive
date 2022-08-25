--liquibase formatted sql

--changeset herronrb:indicators5

create index idx_indicator_values_indicator_id on tbl_indicator_values (indicator_id);

create index idx_indicators_category_id on tbl_indicators (indicator_category_id);

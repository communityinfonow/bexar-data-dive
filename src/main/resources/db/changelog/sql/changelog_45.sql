--liquibase formatted sql

--changeset herronrb:aggregation_prep_5

alter table tbl_indicators add is_aggregable boolean not null default false;
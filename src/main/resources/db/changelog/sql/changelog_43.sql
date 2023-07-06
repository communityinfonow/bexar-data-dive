--liquibase formatted sql

--changeset herronrb:aggregation_prep_3

alter table tbl_indicators add rate_per numeric;
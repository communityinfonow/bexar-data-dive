--liquibase formatted sql

--changeset herronrb:data_corrections_by_source

alter table tbl_data_corrections add source_id numeric references tbl_sources;
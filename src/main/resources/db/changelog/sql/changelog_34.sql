--liquibase formatted sql

--changeset herronrb:admin_3

alter table tbl_faqs add display boolean not null default true;
alter table tbl_sources add display boolean not null default true;
alter table tbl_filter_options add display boolean not null default true;
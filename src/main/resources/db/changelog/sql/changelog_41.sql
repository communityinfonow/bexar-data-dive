--liquibase formatted sql

--changeset herronrb:custom_locations

insert into tbl_location_types (id_, name_en, name_es, sort_order)
values (7, 'Custom Locations', 'Custom Locations (es)', 7);
--liquibase formatted sql

--changeset herronrb:location_indexing

create index idx_locations__location_type_id on tbl_locations (location_type_id);
create index idx_location_geometries__location_type_id on tbl_location_geometries (location_type_id);
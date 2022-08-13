--liquibase formatted sql

--changeset herronrb:indicators2

drop index idx_indicator_values;
create unique index idx_indicator_values on tbl_indicator_values (indicator_id, year_, location_id, location_type_id, coalesce(race_id, 0), coalesce(age_id, 0), coalesce(sex_id, 0), coalesce(education_id, 0), coalesce(income_id, 0));

create index idx_indicator_values_location
on tbl_indicator_values using btree
(indicator_id, location_id, location_type_id);

create index idx_indicator_values_location_year
on tbl_indicator_values using btree
(indicator_id, location_id, location_type_id, year_);
--liquibase formatted sql

--changeset herronrb:data_load_helpers

create table tbl_indicator_values__deleted (
	indicator_id numeric not null references tbl_indicators,
	year_ text not null,
	location_id text not null,
	location_type_id numeric not null,
	indicator_value numeric,
	moe_low numeric,
	moe_high numeric,
	universe_value numeric,
	race_id numeric references tbl_filter_options,
	age_id numeric references tbl_filter_options,
	sex_id numeric references tbl_filter_options,
	education_id numeric references tbl_filter_options,
	income_id numeric references tbl_filter_options,
	suppress boolean default false,
	foreign key(location_id, location_type_id) references tbl_locations(id_, location_type_id)
);

create or replace function delete_indicator_values (ind numeric, yr text) returns void language plpgsql as '
	begin
		-- delete any existing backup data for the indicator/year
		delete from tbl_indicator_values__deleted where indicator_id = ind and year_ = yr;
		-- back up the data for the indicator/year
		insert into tbl_indicator_values__deleted
		select * from tbl_indicator_values where indicator_id = ind and year_ = yr;
		-- delete the data for the indicator/year
		delete from tbl_indicator_values where indicator_id = ind and year_ = yr;
		refresh materialized view mv_indicator_filters;
		refresh materialized view mv_indicator_metadata;
		refresh materialized view mv_indicator_years;
	end;
';

create or replace function restore_indicator_values (ind numeric, yr text) returns void language plpgsql as '
	begin
		-- restore the data for the indicator/year
		insert into tbl_indicator_values
		select * from tbl_indicator_values__deleted where indicator_id = ind and year_ = yr;
		-- delete the backup data for the indicator/year
		delete from tbl_indicator_values__deleted where indicator_id = ind and year_ = yr;
		refresh materialized view mv_indicator_filters;
		refresh materialized view mv_indicator_metadata;
		refresh materialized view mv_indicator_years;
	end;
';
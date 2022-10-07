--liquibase formatted sql

--changeset herronrb:mv_indicator_filter_combos
create materialized view if not exists mv_indicator_filter_combos as
select distinct indicator_id, 
	case when race_id is null then false else true end as race, 
	case when age_id is null then false else true end as age, 
	case when sex_id is null then false else true end as sex, 
	case when education_id is null then false else true end as education, 
	case when income_id is null then false else true end as income
from tbl_indicator_values
order by indicator_id
with data;
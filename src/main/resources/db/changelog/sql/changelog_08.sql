--liquibase formatted sql

--changeset herronrb:indicators7

create materialized view mv_indicator_years as
select distinct iv.indicator_id, iv.year_
from tbl_indicator_values iv
order by iv.indicator_id, iv.year_;

create materialized view mv_indicator_filters as
select distinct i.id_ as indicator_id, ft.id_ as type_id, ft.name_en as type_name_en, ft.name_es as type_name_es,
	fo.id_ as option_id, fo.name_en as option_name_en, fo.name_es as option_name_es, fo.sort_order
from tbl_indicators i
	join tbl_indicator_values iv on i.id_ = iv.indicator_id
	join tbl_filter_options fo on fo.id_ in (iv.race_id, iv.age_id, iv.sex_id, iv.education_id, iv.income_id)
	join tbl_filter_types ft on fo.type_id = ft.id_
order by i.id_, ft.id_, fo.sort_order;

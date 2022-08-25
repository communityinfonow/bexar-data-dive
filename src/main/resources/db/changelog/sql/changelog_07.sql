--liquibase formatted sql

--changeset herronrb:indicators6

create materialized view mv_indicator_metadata as
select i.id_ as indicator_id,
	lt.id_ as location_type_id,
	case when exists (select 1 from tbl_indicator_values where indicator_id = i.id_ and location_type_id = lt.id_) then true else false end as has_data
from tbl_indicators i
	cross join tbl_location_types lt;


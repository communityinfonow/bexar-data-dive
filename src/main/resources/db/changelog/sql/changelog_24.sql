--liquibase formatted sql

--changeset herronrb:indicator_years_mv_update
DROP MATERIALIZED VIEW IF EXISTS public.mv_indicator_years;

CREATE MATERIALIZED VIEW IF NOT EXISTS public.mv_indicator_years
TABLESPACE pg_default
AS
 select indicator_id, year_, array_agg(location_type_id) as location_types
from (
SELECT distinct iv.indicator_id,
    iv.year_, iv.location_type_id
   FROM tbl_indicator_values iv
  ORDER BY iv.indicator_id, iv.year_, iv.location_type_id
) tmp
group by indicator_id, year_

WITH DATA;
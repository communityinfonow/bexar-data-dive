--liquibase formatted sql

--changeset herronrb:refresh_views
CREATE OR REPLACE FUNCTION public.refresh_materialized_views()
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS '
	begin
		refresh materialized view mv_indicator_filter_combos with data;
		refresh materialized view mv_indicator_filters with data;
		refresh materialized view mv_indicator_metadata with data;
		refresh materialized view mv_indicator_years with data;
	end;
';

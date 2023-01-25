--liquibase formatted sql

--changeset herronrb:new_age_filters
insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order)
values (327, 2, 'Under 19 years', 'Under 19 years (es)', 12)
on conflict do nothing;

insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order)
values (328, 2, '19 to 64 years', '19 to 64 years (es)', 13)
on conflict do nothing;
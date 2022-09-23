--liquibase formatted sql

--changeset herronrb:ind_update

update tbl_indicators 
set name_en = 'Child abuse - confirmed victims revictimized within 5 years', 
	name_es = 'Child abuse - confirmed victims revictimized within 5 years (es)',
	indicator_type_id = 3
where id_ = 10;
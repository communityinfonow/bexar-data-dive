--liquibase formatted sql

--changeset herronrb:ind_name_update

update tbl_indicators 
set name_en = 'Child abuse - confirmed victims revictimized within 5 years', 
	name_es = 'Child abuse - confirmed victims revictimized within 5 years (es)'
where id_ = 10;
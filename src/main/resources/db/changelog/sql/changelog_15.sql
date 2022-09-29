--liquibase formatted sql

--changeset herronrb:alice_ind_name_update
update tbl_indicators set 
	name_en = 'ALICE and below poverty households', 
	name_es = 'ALICE and below poverty households (es)'
where id_ = 43;
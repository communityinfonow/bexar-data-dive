--liquibase formatted sql

--changeset herronrb:featured_indicators
update tbl_indicators 
set featured = false;
update tbl_indicators 
set featured = true 
where id_ in (4, 8, 12, 14, 15, 26, 27, 31, 32, 40, 41, 43, 45, 46, 48, 50, 51, 53, 57, 61, 63, 65, 66, 67, 69, 70, 77, 78, 79, 81, 85, 86, 88, 89, 90, 91, 93, 95, 97, 98, 103, 104, 106, 108, 111, 112, 113, 115, 117, 118, 122, 123, 126, 127);

--liquibase formatted sql

--changeset herronrb:src_and_featured_inds_update

update tbl_indicators set featured = false;
update tbl_indicators set featured = true where id_ in (70, 91, 63);

update tbl_sources set name_en = 'C3 Health Information Exchange (C3HIE)', name_es = 'C3 Health Information Exchange (C3HIE)' where id_ = 12;

update tbl_sources set url_ = 'https://www.census.gov/programs-surveys/acs/data.html' where id_ = 1;
update tbl_sources set url_ = 'https://www.bcad.org/' where id_ = 2;
update tbl_sources set url_ = 'https://www.bexar.org/1568/Elections-Department' where id_ = 3;
update tbl_sources set url_ = 'https://www.bexar.org/1360/Juvenile-Probation' where id_ = 4;
update tbl_sources set url_ = 'https://www.cdc.gov/cancer/dcpc/data/index.htm' where id_ = 5;
update tbl_sources set url_ = 'https://data.sanantonio.gov/' where id_ = 6;
update tbl_sources set url_ = 'https://www.sanantonio.gov/NHSD' where id_ = 7;
update tbl_sources set url_ = 'https://www.census.gov/programs-surveys/decennial-census/decade/2020/2020-census-results.html' where id_ = 8;
update tbl_sources set url_ = 'https://www.readykidsa.com/providers-resources/' where id_ = 9;
update tbl_sources set url_ = 'https://www.epa.gov/ejscreen' where id_ = 10;
update tbl_sources set url_ = 'https://www.fbi.gov/services/cjis/ucr' where id_ = 11;
update tbl_sources set url_ = 'https://c3hie.org/' where id_ = 12;
update tbl_sources set url_ = 'https://www.hhs.texas.gov/about/records-statistics/data-statistics' where id_ = 13;
update tbl_sources set url_ = 'https://www.huduser.gov/portal/pdrdatas_landing.html' where id_ = 14;
update tbl_sources set url_ = 'https://lehd.ces.census.gov/' where id_ = 15;
update tbl_sources set url_ = 'https://www.cdc.gov/nchs/products/life_tables.htm' where id_ = 16;
update tbl_sources set url_ = 'https://preservationdatabase.org/' where id_ = 17;
update tbl_sources set url_ = 'https://www.cdc.gov/places/index.html' where id_ = 18;
update tbl_sources set url_ = 'https://www.sanantonio.gov/Health/AboutUs/SAForward' where id_ = 19;
update tbl_sources set url_ = 'https://www.sanantonio.gov/SAPD' where id_ = 20;
update tbl_sources set url_ = 'https://www.dfps.state.tx.us/' where id_ = 21;
update tbl_sources set url_ = 'https://www.dps.texas.gov/section/crime-records' where id_ = 22;
update tbl_sources set url_ = 'https://dshs.texas.gov/datareports.shtm' where id_ = 23;
update tbl_sources set url_ = 'https://www.dshs.texas.gov/thcic/hospitals/Inpatientpudf.shtm' where id_ = 24;
update tbl_sources set url_ = 'https://www.uwsatx.org/' where id_ = 25;
update tbl_sources set url_ = 'https://www.census.gov/data/developers/data-sets/decennial-response-rates/2020.html' where id_ = 26;
update tbl_sources set url_ = 'https://www.ers.usda.gov/data-products/food-access-research-atlas/' where id_ = 27;
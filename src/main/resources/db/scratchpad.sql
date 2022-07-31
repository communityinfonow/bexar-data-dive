select *
from (
select --*,
  ic.id_ as indicator_category_id, ic.name_en as indicator_category_name_en, ic.name_es as indicator_category_name_es,
  i.id_ as indicator_id, i.name_en as indicator_name_en, i.name_es as indicator_name_es,
  iv.year_, iv.indicator_value, iv.moe_low, iv.moe_high, iv.universe_value,
  s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es,
  fr.id_ as race_id, fr.name_en as race_name_en, fr.name_es as race_name_es,
  fb.id_ as base_filter_id, fb.name_en as base_filter_name_en, fb.name_es as base_filter_name_es,
  rank() over(partition by iv.indicator_id order by iv.year_ desc) 
from tbl_indicator_categories ic
  join tbl_indicators i on i.indicator_category_id = ic.id_
  left join tbl_indicator_values iv on iv.indicator_id = i.id_ and iv.location_id = 1 and iv.location_type_id = 1
  left join tbl_sources s on s.id_ = iv.source_id
  left join tbl_filter_options fr on fr.id_ = iv.race_id
  left join tbl_filter_options fb on fb.id_ = case 
      when i.base_filter_type_id = 2 then iv.age_id 
      when i.base_filter_type_id = 3 then iv.sex_id
      when i.base_filter_type_id = 4 then iv.education_id
      when i.base_filter_type_id = 5 then iv.income_id 
    end
order by ic.sort_order, i.id_, fb.sort_order, fr.sort_order
) ranked_data
where rank = 1
;


drop table databasechangeloglock;
drop table databasechangelog;
drop table tbl_location_geometries;
drop table tbl_indicator_values;
drop table tbl_indicators;
drop table tbl_indicator_types;
drop table tbl_indicator_categories;
drop table tbl_sources;
drop table tbl_filter_options;
drop table tbl_filter_types;
drop table tbl_locations;
drop table tbl_location_types;

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','48029',1,487,4,4,487,1,null,null,null,null),
	(1,'2020','48029',1,18,12,12,487,2,null,null,null,null),
	(1,'2020','48029',1,71,9,9,487,3,null,null,null,null),
	(2,'2020','48029',1,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','48029',1,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','48029',1,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','48029',1,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','48029',1,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','48029',1,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','48029',1,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','48029',1,482,4,4,482,1,null,null,null,null),
	(1,'2016','48029',1,16,12,12,482,2,null,null,null,null),
	(1,'2016','48029',1,70,9,9,482,3,null,null,null,null),
	(3,'2016','48029',1,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','48029',1,18.3,3,7,482,2,null,null,11,null);


insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','2',4,487,4,4,487,1,null,null,null,null),
	(1,'2020','2',4,18,12,12,487,2,null,null,null,null),
	(1,'2020','2',4,71,9,9,487,3,null,null,null,null),
	(2,'2020','2',4,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','2',4,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','2',4,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','2',4,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','2',4,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','2',4,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','2',4,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','2',4,482,4,4,482,1,null,null,null,null),
	(1,'2016','2',4,16,12,12,482,2,null,null,null,null),
	(1,'2016','2',4,70,9,9,482,3,null,null,null,null),
	(3,'2016','2',4,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','2',4,18.3,3,7,482,2,null,null,11,null);

  insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','3',4,487,4,4,487,1,null,null,null,null),
	(1,'2020','3',4,18,12,12,487,2,null,null,null,null),
	(1,'2020','3',4,71,9,9,487,3,null,null,null,null),
	(2,'2020','3',4,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','3',4,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','3',4,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','3',4,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','3',4,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','3',4,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','3',4,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','3',4,482,4,4,482,1,null,null,null,null),
	(1,'2016','3',4,16,12,12,482,2,null,null,null,null),
	(1,'2016','3',4,70,9,9,482,3,null,null,null,null),
	(3,'2016','3',4,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','3',4,18.3,3,7,482,2,null,null,11,null);

  insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','4',4,487,4,4,487,1,null,null,null,null),
	(1,'2020','4',4,18,12,12,487,2,null,null,null,null),
	(1,'2020','4',4,71,9,9,487,3,null,null,null,null),
	(2,'2020','4',4,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','4',4,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','4',4,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','4',4,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','4',4,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','4',4,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','4',4,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','4',4,482,4,4,482,1,null,null,null,null),
	(1,'2016','4',4,16,12,12,482,2,null,null,null,null),
	(1,'2016','4',4,70,9,9,482,3,null,null,null,null),
	(3,'2016','4',4,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','4',4,18.3,3,7,482,2,null,null,11,null);

  insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2020','5',4,487,4,4,487,1,null,null,null,null),
	(1,'2020','5',4,18,12,12,487,2,null,null,null,null),
	(1,'2020','5',4,71,9,9,487,3,null,null,null,null),
	(2,'2020','5',4,52430,2300,1700,null,1,null,null,null,null),
	(2,'2020','5',4,51430,1300,700,null,2,null,null,null,null),
	(2,'2020','5',4,53430,1300,700,null,3,null,null,null,null),
	(3,'2020','5',4,28.5,2,6,487,1,null,null,10,null),
	(3,'2020','5',4,31.4,3,7,487,1,null,null,11,null),
	(3,'2020','5',4,17.6,2,6,43,2,null,null,10,null),
	(3,'2020','5',4,22.8,3,7,43,2,null,null,11,null);

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (1,'2016','5',4,482,4,4,482,1,null,null,null,null),
	(1,'2016','5',4,16,12,12,482,2,null,null,null,null),
	(1,'2016','5',4,70,9,9,482,3,null,null,null,null),
	(3,'2016','5',4,25.1,2,6,482,1,null,null,10,null),
	(3,'2016','5',4,18.3,3,7,482,2,null,null,11,null);

update tbl_indicator_values set indicator_value = indicator_value + location_id::numeric;

insert into tbl_indicator_values (indicator_id,year_,location_id,location_type_id,indicator_value,moe_low,moe_high,universe_value,race_id,age_id,sex_id,education_id,income_id)
values (21, '2020', '48029182106', 5, 88, 2, 2, 20000, null, 4, null, null, null),
(21, '2015', '48029182106', 5, 86, 2, 2, 20000, null, 4, null, null, null),
(21, '2010', '48029182106', 5, 90, 2, 2, 20000, null, 4, null, null, null),
(21, '2020', '48029161304', 5, 88, 2, 2, 20000, null, 4, null, null, null),
(21, '2015', '48029161304', 5, 86, 2, 2, 20000, null, 4, null, null, null),
(21, '2010', '48029161304', 5, 90, 2, 2, 20000, null, 4, null, null, null),
(21, '2020', '48029121118', 5, 88, 2, 2, 20000, null, 4, null, null, null),
(21, '2015', '48029121118', 5, 86, 2, 2, 20000, null, 4, null, null, null),
(21, '2010', '48029121118', 5, 90, 2, 2, 20000, null, 4, null, null, null)
;

update tbl_indicators set featured = true where id_ in (1, 7, 12);

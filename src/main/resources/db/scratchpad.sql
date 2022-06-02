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
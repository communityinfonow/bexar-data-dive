package org.cinow.omh.mycommunity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.indicators.IndicatorCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MyCommunityRepositoryPostgresql implements MyCommunityRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<MyCommunityDataCategory> getMyCommunityData(long location, long locationType) {
		String sql = ""
			+ " select * "
			+ " from ( "
			+ " select "
			+ "   ic.id_ as category_id, ic.name_en as category_name_en, ic.name_es as category_name_es, "
			+ "   i.id_ as indicator_id, i.name_en as indicator_name_en, i.name_es as indicator_name_es, "
			+ "   iv.year_, iv.indicator_value, iv.moe_low, iv.moe_high, iv.universe_value, "
			+ "   s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, "
			+ "   fr.id_ as race_id, fr.name_en as race_name_en, fr.name_es as race_name_es, "
			+ "   fb.id_ as base_filter_id, fb.name_en as base_filter_name_en, fb.name_es as base_filter_name_es, "
			+ "   rank() over(partition by iv.indicator_id order by iv.year_ desc) "
			+ " from tbl_indicator_categories ic "
			+ "   join tbl_indicators i on i.indicator_category_id = ic.id_ "
			+ "   left join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ "     and iv.location_id = :location_id and iv.location_type_id = :location_type_id "
			+ "   left join tbl_sources s on s.id_ = iv.source_id "
			+ "   left join tbl_filter_options fr on fr.id_ = iv.race_id "
			+ "   left join tbl_filter_options fb on fb.id_ = case "
			+ "     when i.base_filter_type_id = 2 then iv.age_id "
			+ "     when i.base_filter_type_id = 3 then iv.sex_id "
			+ "     when i.base_filter_type_id = 4 then iv.education_id "
			+ "     when i.base_filter_type_id = 5 then iv.income_id "
			+ "   end "
			+ " order by ic.sort_order, i.id_, fb.sort_order, fr.sort_order "
			+ " ) ranked_data "
			+ " where rank = 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_id", location);
		paramMap.addValue("location_type_id", locationType);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<MyCommunityDataCategory>>() {
			@Override
			public List<MyCommunityDataCategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<MyCommunityDataCategory> communityData = new ArrayList<>();
				MyCommunityDataCategory categoryData = null;
				MyCommunityDataIndicator indicatorData = null;
				while (rs.next()) {
					if (categoryData == null || categoryData.getCategory().getId() != rs.getLong("category_id")) {
						categoryData = new MyCommunityDataCategory();
						categoryData.setCategory(new IndicatorCategory());
						categoryData.getCategory().setId(rs.getLong("category_id"));
						categoryData.getCategory().setName_en(rs.getString("category_name_en"));
						categoryData.getCategory().setName_en(rs.getString("category_name_es"));
						communityData.add(categoryData);
					}
					if (indicatorData == null || indicatorData.get)
				}

				return communityData;
			}
		});
	}
}

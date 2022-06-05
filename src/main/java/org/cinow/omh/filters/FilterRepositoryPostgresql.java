package org.cinow.omh.filters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilterRepositoryPostgresql implements FilterRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Filter> getFilters(long indicatorId) {
		String sql = ""
			+ " select ft.id_ as type_id, ft.name_en as type_name_en, ft.name_es as type_name_es, "
			+ " 	fo.id_ as option_id, fo.name_en as option_name_en, fo.name_es as option_name_es "
			+ " from tbl_indicators i "
			+ " 	join tbl_indicator_values iv on i.id_ = iv.indicator_id "
			+ " 	join tbl_filter_options fo on fo.id_ in (iv.race_id, iv.age_id, iv.sex_id, iv.education_id, iv.income_id) "
			+ " 	join tbl_filter_types ft on fo.type_id = ft.id_ "
			+ " where i.id_ = :indicator_id "
			+ " order by ft.id_, fo.sort_order ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<Filter>>() {
			@Override
			public List<Filter> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				
				List<Filter> filters = new ArrayList<>();
				Filter filter = new Filter();
				long typeId = 0;
				while (rs.next()) {
					if (rs.getLong("type_id") != typeId) {
						filter = new Filter();
						FilterType type = new FilterType();
						type.setId(rs.getLong("type_id"));
						type.setName_en(rs.getString("type_name_en"));
						type.setName_es(rs.getString("type_name_es"));
						filter.setType(type);
						filters.add(filter);
					}
					FilterOption option = new FilterOption();
					option.setId(rs.getLong("option_id"));
					option.setName_en(rs.getString("option_name_en"));
					option.setName_es(rs.getString("option_name_es"));
					filter.getOptions().add(option);
				}
				return filters;
			}	
		});
	}
	
}

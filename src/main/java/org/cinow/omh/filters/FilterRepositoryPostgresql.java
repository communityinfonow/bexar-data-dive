package org.cinow.omh.filters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilterRepositoryPostgresql implements FilterRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Filter getLocationTypeFilter(String indicatorId) {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from tbl_location_types "
			+ " where exists (select 1 from tbl_indicator_values where indicator_id = :indicator_id::numeric and location_type_id = id_) "
			+ " order by sort_order ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setName_en("Location Type");
				filter.getType().setName_es("Location Type (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("id_"));
					option.setName_en(rs.getString("name_en"));
					option.setName_es(rs.getString("name_es"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	@Override
	public Filter getLocationFilter() {
		String sql = ""
			+ " select l.id_, l.location_type_id, l.name_en, l.name_es "
			+ " from tbl_locations l "
			+ " 	join tbl_location_types lt on lt.id_ = l.location_type_id "
			+ " order by lt.sort_order, l.id_::numeric ";

		return this.jdbcTemplate.query(sql, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setName_en("Location");
				filter.getType().setName_es("Location (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("id_"));
					option.setName_en(rs.getString("name_en"));
					option.setName_es(rs.getString("name_es"));
					option.setTypeId(rs.getString("location_type_id"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	@Override
	public Filter getYearFilter(String indicatorId) {
		String sql = ""
			+ " select distinct year_ "
			+ " from tbl_indicator_values "
			+ " where indicator_id = :indicator_id::numeric "
			+ " order by year_ desc ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setName_en("Year");
				filter.getType().setName_es("Year (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("year_"));
					option.setName_en(rs.getString("year_"));
					option.setName_es(rs.getString("year_"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	@Override
	public List<Filter> getIndicatorFilters(String indicatorId) {
		String sql = ""
			+ " select distinct ft.id_ as type_id, ft.name_en as type_name_en, ft.name_es as type_name_es, "
			+ " 	fo.id_ as option_id, fo.name_en as option_name_en, fo.name_es as option_name_es, fo.sort_order "
			+ " from tbl_indicators i "
			+ " 	join tbl_indicator_values iv on i.id_ = iv.indicator_id "
			+ " 	join tbl_filter_options fo on fo.id_ in (iv.race_id, iv.age_id, iv.sex_id, iv.education_id, iv.income_id) "
			+ " 	join tbl_filter_types ft on fo.type_id = ft.id_ "
			+ " where i.id_ = :indicator_id::numeric "
			+ " order by ft.id_, fo.sort_order ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<Filter>>() {
			@Override
			public List<Filter> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				
				List<Filter> filters = new ArrayList<>();
				Filter filter = new Filter();
				String typeId = "0";
				while (rs.next()) {
					if (!rs.getString("type_id").equals(typeId)) {
						typeId = rs.getString("type_id");
						filter = new Filter();
						FilterType type = new FilterType();
						type.setId(rs.getString("type_id"));
						type.setName_en(rs.getString("type_name_en"));
						type.setName_es(rs.getString("type_name_es"));
						filter.setType(type);
						filters.add(filter);
					}
					FilterOption option = new FilterOption();
					option.setId(rs.getString("option_id"));
					option.setName_en(rs.getString("option_name_en"));
					option.setName_es(rs.getString("option_name_es"));
					filter.getOptions().add(option);
				}
				return filters;
			}	
		});
	}
}
